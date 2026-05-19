# Serviço Auditor de Falhas DLQ


## Por que escolhi a Arquitetura Hexagonal?

Pensei bastante em qual arquitetura usar. Cogitei o MVC que é o mais comum,
mas esse serviço não tem controller REST nem tela, então o MVC não fazia
muito sentido aqui.

A hexagonal fez mais sentido porque esse serviço tem uma responsabilidade
muito específica e bem definida: receber mensagem, calcular severidade e
salvar. A hexagonal é boa exatamente pra isso ela isola o domínio de
tudo que é detalhe técnico externo.

Outro motivo foi a flexibilidade. Se um dia precisar trocar o banco H2 por
Postgres, ou sair do SQS pra outro serviço de fila, só mudo a camada de
infraestrutura. As regras de negócio continuam intactas. Isso é o grande
benefício da hexagonal: o núcleo do sistema não quebra por causa de
mudanças externas.



## Como organizei as pastas e por que fiz assim

### core/domain — o coração do sistema

Essa é a camada mais importante. Aqui ficam as regras de negócio puras,
sem nenhuma dependência externa. Não tem anotação de Spring, não tem JPA,
não tem nada de AWS. É Java puro.

**AuditErrorBO** — é o objeto de negócio que representa um erro de
auditoria. Ele trafega entre todas as camadas internas do sistema. Usei
BO (Business Object) no nome pra deixar claro que é um objeto do domínio,
diferente da entidade JPA que existe só pra comunicar com o banco.

**SeverityEnum** — define os três níveis de severidade possíveis: LOW,
MEDIUM e HIGH. Coloquei no domínio porque severidade é um conceito de
negócio, não um detalhe técnico.

**StatusEnum** — define o status do registro. Por enquanto só tem
PENDING_ANALYSIS, que indica que o erro foi registrado e está aguardando
análise de alguém.

**DomainException** — é uma exceção própria do domínio. Quando algo dá
errado nas regras de negócio, o sistema lança essa exceção em vez de uma
genérica. Isso deixa mais claro de onde veio o erro.



### application/ports — os contratos do sistema

Aqui ficam as interfaces que definem o que o sistema oferece e o que ele
precisa. Na hexagonal essas interfaces são chamadas de "ports" — são como
tomadas, definem o formato da conexão sem se preocupar com o que está
plugado.

**DlqAuditServicePort** — é o port de entrada. Define o método
processarMensagem(String payload) que qualquer adapter de entrada precisa
chamar. O listener do SQS não conhece a implementação do serviço, só
conhece essa interface. Isso separa completamente a infraestrutura do
domínio.

**AuditErrorRepositoryPort** — é o port de saída. Define o método
salvar(AuditErrorBO) que o serviço usa pra persistir os dados. O serviço
não sabe se vai salvar no H2, Postgres ou qualquer outro banco. Ele só
chama essa interface e quem implementa resolve.

Essa separação de ports de entrada e saída é um dos conceitos mais
importantes da hexagonal. O domínio fica no meio, recebendo pelo port de
entrada e saindo pelo port de saída, sem depender de ninguém.

---

### application/services — onde as regras vivem

**DlqAuditService** — essa é a classe mais importante do projeto. É aqui
que a lógica de negócio acontece de verdade.

Quando uma mensagem chega, o serviço faz três coisas:

Primeiro Transforma o payload JSON pra um objeto OrderEventDTO usando
o Jackson. Segundo soma a quantidade de todos os itens do pedido e calcula
a severidade, se passou de 100 itens é HIGH, entre 50 e 100 é MEDIUM,
menos de 50 é LOW. Terceiro monta o objeto AuditErrorBO com todas as
informações e manda salvar pelo port de saída.

Coloquei a lógica de severidade aqui e não no listener porque é uma regra
de negócio. Regra de negócio fica no service, nunca na infraestrutura. Se
essa lógica estivesse no listener, estaria misturando responsabilidade de
infraestrutura com regra de negócio, o que quebraria os princípios da
arquitetura.

---

### infrastructure/adapters/in/sqs — entrada pelo SQS

**DlqListenerAdapter** — é o adapter de entrada. Ele usa a anotação
@SqsListener pra ficar escutando a fila DLQ. Quando chega uma mensagem,
ele simplesmente repassa o payload pro serviço através do port de entrada.
Não tem nenhuma regra de negócio aqui, só recebe e repassa.

Coloquei na infraestrutura porque o SQS é um detalhe técnico externo. O
domínio não precisa saber que as mensagens vêm de uma fila. Pra ele só
importa que chegou um payload pra processar.

**OrderEventDTO e OrderItemDTO** — são os objetos que representam o formato
da mensagem que vem da fila. Ficam na infraestrutura porque são contratos
externos, dependem do formato que a fila envia, não do que o domínio quer.

---

### infrastructure/adapters/out/persistence/h2 — saída pro banco

**AuditErrorEntity** — é a entidade JPA mapeada pra tabela audit_errors
no banco. Separei ela do AuditErrorBO de propósito. O BO é do domínio,
a Entity é da infraestrutura. Misturar os dois seria errado na hexagonal
porque o domínio ficaria dependendo de anotações JPA.

**AuditErrorJpaRepository** — é a interface que extende o JpaRepository
do Spring Data. O Spring cria a implementação automaticamente em tempo de
execução. Só precisa declarar a interface.

**AuditErrorMapper** — faz a conversão entre AuditErrorBO e
AuditErrorEntity. Existe porque o domínio não pode conhecer a entidade
JPA e a entidade não pode conhecer o BO. O mapper fica no meio fazendo
essa ponte.

**AuditErrorRepositoryAdapter** — implementa o AuditErrorRepositoryPort.
É aqui que a mágica acontece: o service chama o port de saída, o Spring
injeta esse adapter, e ele usa o JpaRepository pra salvar no banco. O
service nunca soube que existia H2 ou JPA — só viu a interface.

---
