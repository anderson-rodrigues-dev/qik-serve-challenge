# QikServe Engineer Technical Test
Respostas às Perguntas de Acompanhamento
1. Quanto tempo você gastou no teste? O que você adicionaria se tivesse mais tempo?
Passei aproximadamente X horas no desenvolvimento do teste técnico.
Se tivesse mais tempo, eu adicionaria:

Melhorias em Multi-Tenancy: Automatizar e integrar a escolha de diferentes wiremocks para múltiplos POS.
Testes de carga e segurança adicionais: Garantir que a API seja escalável e segura contra ataques como injeção de SQL ou XSS.
Melhorias na internacionalização: Incorporar suporte a mais idiomas e testar traduções em diferentes cenários.
2. Qual foi a funcionalidade mais útil adicionada na última versão da linguagem escolhida? Inclua um trecho de código que demonstre como você a utilizou.
A funcionalidade mais útil foi o Record no Java (introduzido no Java 14 e estabilizado no Java 16). Ele simplifica a criação de classes imutáveis e melhora a legibilidade do código.

Exemplo de uso no projeto:

java
Copiar
Editar
public record ExceptionResponse(Date timestamp, String message, String details) implements Serializable {
    private static final long serialVersionUID = 1L;
}
Isso foi usado para padronizar a resposta de exceções na API, reduzindo o código boilerplate.

3. O que você achou mais difícil?
Gerenciamento de Promoções Complexas: Implementar a lógica para múltiplos tipos de promoções e garantir que fossem aplicadas corretamente, mesmo com diferentes combinações de itens.
Multi-Tenancy do WireMock: Configurar várias instâncias do WireMock para suportar múltiplos POS foi desafiador devido à necessidade de customização e automação.
4. Qual mecanismo você implementou para rastrear problemas em produção? O que poderia fazer?
Implementei:

Logging estruturado com SLF4J: Para capturar eventos relevantes da API e facilitar a identificação de problemas.
Tratamento centralizado de exceções: A classe GlobalExceptionHandler padroniza as respostas de erro e fornece informações úteis para depuração.
O que eu poderia fazer:

Monitoramento com ferramentas como Prometheus e Grafana: Para capturar métricas de desempenho em tempo real.
Tracing distribuído com OpenTelemetry: Para rastrear requisições entre serviços e identificar gargalos.
5. Explique sua interpretação da lista de requisitos e o que foi entregue ou não entregue e por quê.
Requisitos interpretados:

MUST Have: A API foi desenvolvida para consumir o WireMock, aplicar promoções e calcular preços corretamente.
SHOULD: Adicionei suporte à internacionalização para nomes de produtos, conforme especificado.
COULD: Foi implementada a base para multi-tenancy, com múltiplos WireMocks representando POS diferentes.
WON'T HAVE: Não desenvolvi uma interface gráfica, pois não era um foco do teste.
Entregas:

MUST: API funcional com lógica de checkout, promoções e cálculo de preços.
SHOULD: Internacionalização concluída para en, pt e es.
COULD: Suporte básico a multi-tenancy com WireMock.
O que não foi entregue:

Testes mais robustos para cenários de multi-tenancy devido à limitação de tempo.
Diagrama do Processo de Checkout
(Adicione um diagrama aqui para ilustrar o processo. Exemplo: um diagrama de sequência ou fluxo de dados.)

