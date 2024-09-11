# Parking Management API

A Spring Boot API for managing parking establishments, vehicle registrations, and vehicle entry/exit transactions. The API provides various endpoints for creating and updating establishments, managing vehicles, and generating reports on parking activity.

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.3**
- **Maven**
- **MongoDB**
- **Springdoc OpenAPI (Swagger)** for API documentation

## Setup Instructions

### Prerequisites

Make sure you have the following installed:

- **Java 17**
- **Maven**
- **MongoDB**

### How to run

1. Clone the repository:
    ```bash
    git clone https://github.com/fcamarasantos/backend-test-java.git
    ```

2. Navigate to the project folder:
    ```bash
    cd backend-test-java
    ```

3. Install dependencies and package the project using Maven:
    ```bash
    mvn clean install
    ```

4. Configure the `application.yml` file with your MongoDB settings:

    ```yaml
    spring:
      data:
        mongodb:
          uri: mongodb://localhost:27017/db_parking_management
    ```

5. Run the application:
    ```bash
    mvn spring-boot:run
    ```

### API Documentation

Once the application is running, you can access the API documentation via Swagger:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)

### API Endpoints

#### Establishment Management

- **Create an establishment**

    ```http
    POST /establishments
    ```

  Request body example:

    ```json
    {
      "name": "Parking Lot A",
      "cnpj": "12345678901234",
      "address": "123 Main Street",
      "phone": "(11) 98765-4321",
      "motorcycleSpots": 10,
      "carSpots": 20
    }
    ```

- **Get all establishments**

    ```http
    GET /establishments
    ```

- **Get establishment by ID**

    ```http
    GET /establishments/{id}
    ```

- **Update establishment by ID**

    ```http
    PUT /establishments/{id}
    ```

  Request body example:

    ```json
    {
      "name": "Parking Lot B",
      "cnpj": "12345678901234",
      "address": "456 Another Street",
      "phone": "(11) 98765-4321",
      "motorcycleSpots": 15,
      "carSpots": 25
    }
    ```

- **Delete establishment by ID**

    ```http
    DELETE /establishments/{id}
    ```

#### Vehicle Management

- **Create a vehicle**

    ```http
    POST /vehicles
    ```

  Request body example:

    ```json
    {
      "brand": "Honda",
      "model": "Civic",
      "color": "Black",
      "plate": "ABC-1234",
      "type": "CAR"
    }
    ```

- **Get all vehicles**

    ```http
    GET /vehicles
    ```

- **Get vehicle by ID**

    ```http
    GET /vehicles/{id}
    ```

- **Update vehicle by ID**

    ```http
    PUT /vehicles/{id}
    ```

- **Delete vehicle by ID**

    ```http
    DELETE /vehicles/{id}
    ```

#### Parking Management

- **Register vehicle entry**

    ```http
    POST /parking/entry
    ```

  Request parameters:
    - `establishmentId`: ID of the establishment
    - `plate`: Vehicle plate number

- **Register vehicle exit**

    ```http
    POST /parking/exit
    ```

  Request parameters:
    - `establishmentId`: ID of the establishment
    - `plate`: Vehicle plate number

#### Parking Reports

- **Get parking summary**

    ```http
    GET /reports/summary/{establishmentId}
    ```

- **Get hourly parking summary**

    ```http
    GET /reports/hourly-summary/{establishmentId}
    ```

  This returns the total entries and exits of vehicles by hour.

- **Get vehicle movement report**

    ```http
    GET /reports/vehicle-movements/{establishmentId}
    ```

  Request parameters:
    - `startDate`: Start date in the format `YYYY-MM-DD`
    - `endDate`: End date in the format `YYYY-MM-DD`

## Configuration

- **MongoDB URI**: Set your MongoDB connection in the `application.yml` file.
    ```yaml
    spring:
      data:
        mongodb:
          uri: mongodb://localhost:27017/parking-management
    ```
---
## Questionário para Avaliação de Competências

### Banco de Dados (Nível Básico)

#### Pergunta 1: Explique os principais conceitos de um banco de dados relacional, como tabelas, chaves primárias e estrangeiras.

- **Tabelas**: Conjuntos de dados que armazenam informações relacionadas. Cada tabela contém colunas que definem o tipo de dados e linhas que armazenam as entradas.
- **Chave Primária**: Um campo (ou combinação de campos) em uma tabela que identifica exclusivamente cada registro da tabela. Ela garante que não haja registros duplicados.
- **Chave Estrangeira**: Um campo em uma tabela que estabelece uma relação com uma chave primária em outra tabela. Ela é usada para manter a integridade referencial entre os dados de tabelas diferentes.

#### Pergunta 2: No contexto de uma aplicação de gerenciamento de estacionamento, como você organizaria a modelagem de dados para suportar as funcionalidades de controle de entrada e saída de veículos?

Para uma aplicação de gerenciamento de estacionamento, a modelagem de dados seria organizada com as seguintes tabelas e campos principais:

- **Establishment (Estabelecimento)**:
    - `id`: Chave primária
    - `name`: Nome do estabelecimento
    - `address`: Endereço
    - `cnpj`: CNPJ do estabelecimento
    - `carSpots`: Número de vagas para carros
    - `motorcycleSpots`: Número de vagas para motos

- **Vehicle (Veículo)**:
    - `id`: Chave primária
    - `plate`: Placa do veículo (único)
    - `brand`: Marca do veículo
    - `model`: Modelo do veículo
    - `color`: Cor do veículo
    - `type`: Tipo (Carro ou Moto)
  
- **ParkingTransaction (Transação de Estacionamento)**:
    - `id`: Chave primária
    - `vehicle_id`: Chave estrangeira referenciando a tabela de veículos
    - `establishment_id`: Chave estrangeira referenciando a tabela de estabelecimentos
    - `entryDate`: Data e hora da entrada
    - `exitDate`: Data e hora da saída

#### Pergunta 3: Quais seriam as vantagens e desvantagens de utilizar um banco de dados NoSQL neste projeto?
**Vantagens**:
- **Escalabilidade Horizontal**: Bancos NoSQL, como MongoDB, escalam facilmente à medida que a aplicação cresce, o que pode ser vantajoso se o número de estacionamentos e veículos for muito grande.
- **Flexibilidade no Schema**: O NoSQL oferece flexibilidade no modelo de dados, permitindo a inserção de registros sem um esquema rígido, o que pode ser útil quando os dados dos veículos ou estabelecimentos possuem atributos variáveis.
- **Desempenho**: NoSQL pode oferecer melhor desempenho em grandes volumes de dados, especialmente para leitura e escrita rápidas.

**Desvantagens**:
- **Falta de Integridade Referencial**: Ao contrário dos bancos de dados relacionais, os bancos NoSQL não possuem chaves estrangeiras para garantir a integridade dos dados, o que pode resultar em inconsistências de dados se não forem gerenciados corretamente pela aplicação.
- **Consultas Complexas**: Em um banco de dados relacional, é fácil fazer junções complexas para recuperar dados relacionados. Em NoSQL, essas junções precisam ser feitas manualmente na aplicação, o que pode ser mais complicado e ineficiente.
- **Curva de Aprendizado**: Bancos NoSQL exigem uma nova abordagem em relação aos bancos relacionais, o que pode demandar mais tempo para adaptação da equipe de desenvolvimento.
---
#### Agilidade (Nível Básico)
**Pergunta 1: Explique o conceito de metodologias ágeis e como elas impactam o desenvolvimento de software.**

As metodologias ágeis são abordagens de desenvolvimento de software que promovem a entrega incremental de soluções, focando em interações contínuas e melhorias constantes. Ao contrário de abordagens tradicionais, como o modelo cascata, as metodologias ágeis dividem o projeto em pequenos ciclos (sprints) que permitem a adaptação às mudanças rapidamente. Isso permite que equipes entreguem software funcional em menos tempo, reduzindo o risco de erros e melhorando a capacidade de responder a novas exigências ou mudanças de mercado.

As metodologias ágeis impactam o desenvolvimento de software por meio de:
- **Entregas frequentes**: Software é entregue de forma contínua, garantindo funcionalidades em ciclos curtos.
- **Colaboração contínua com o cliente**: Feedback rápido e ajustes constantes, garantindo que as expectativas do cliente sejam atendidas.
- **Melhoria contínua**: Revisões frequentes do trabalho e do processo, sempre buscando aprimoramentos.

**Pergunta 2: No desenvolvimento deste projeto, como você aplicaria princípios ágeis para garantir entregas contínuas e com qualidade?**

Para aplicar princípios ágeis neste projeto, eu usaria os seguintes métodos:
- **Divisão do trabalho em sprints curtos**: Eu dividiria o desenvolvimento das funcionalidades (como CRUD de veículos e controle de entradas/saídas) em ciclos de uma a duas semanas. Cada sprint teria metas claras e entregaria uma parte funcional do sistema.
- **Reuniões diárias (Daily)**: A equipe teria reuniões curtas diárias para discutir o progresso, identificar bloqueios e ajustar o planejamento se necessário.
- **Feedback constante**: Após cada sprint, entregas seriam avaliadas pelo cliente ou pelos stakeholders para garantir que o sistema está alinhado com as necessidades reais. Com base no feedback, as prioridades poderiam ser ajustadas para a próxima sprint.

**Pergunta 3: Qual a importância da comunicação entre as equipes em um ambiente ágil? Dê exemplos de boas práticas.**

A comunicação é um pilar fundamental em ambientes ágeis, pois promove o alinhamento contínuo entre todos os membros da equipe e stakeholders. A ausência de comunicação clara pode levar a mal-entendidos, atrasos e entrega de funcionalidades que não atendem às expectativas do cliente.

Exemplos de boas práticas de comunicação em um ambiente ágil incluem:
- **Daily**: Reuniões diárias de curta duração onde todos compartilham o progresso, dificuldades e o que planejam trabalhar. Isso garante que todos estejam cientes do estado atual do projeto e que impedimentos sejam rapidamente resolvidos.
- **Retrospectivas de Sprint**: Ao final de cada sprint, a equipe se reúne para discutir o que funcionou bem, o que pode ser melhorado e quais ações serão tomadas para aprimorar o processo nas próximas sprints.
- **Kanban ou Scrum Boards**: O uso de boards visuais (como Jira, Azure ou Trello) ajuda a manter o status de cada tarefa visível para toda a equipe, promovendo transparência e facilitando a identificação de gargalos.
- **Feedback constante**: Comunicação com o cliente ou stakeholders durante o desenvolvimento, garantindo que o feedback sobre funcionalidades seja incorporado rapidamente.

---
#### DevOps (Nível Básico)

**Pergunta 1: O que é DevOps e qual a sua importância para o ciclo de vida de uma aplicação?**

DevOps é uma cultura e um conjunto de práticas que integram as equipes de desenvolvimento de software (Dev) e operações de TI (Ops) para melhorar a eficiência e a colaboração em todo o ciclo de vida de uma aplicação. O principal objetivo do DevOps é acelerar o processo de desenvolvimento, automatizando tarefas repetitivas, garantindo a integração contínua e facilitando o deploy rápido e seguro das aplicações.

A importância do DevOps no ciclo de vida de uma aplicação é imensa:
- **Automação de processos**: Automatiza tarefas manuais, como integração de código, testes e deploy.
- **Agilidade**: Reduz o tempo de entrega de novas funcionalidades e correções de bugs.
- **Confiabilidade**: Garante que a aplicação seja entregue de forma consistente e previsível, com menos chances de erros humanos durante o deploy.
- **Monitoramento contínuo**: Permite que a equipe de operações monitore a aplicação em produção para identificar e corrigir problemas rapidamente.
- **Feedback rápido**: Melhora o feedback sobre o estado da aplicação, promovendo um ciclo rápido de melhorias.

**Pergunta 2: Descreva como você integraria práticas de DevOps no desenvolvimento desta aplicação de estacionamento. Inclua exemplos de CI/CD.**

Para integrar práticas de DevOps no desenvolvimento desta aplicação de gerenciamento de estacionamento, eu aplicaria as seguintes estratégias:

- **Integração Contínua (CI)**:
  Utilizaria ferramentas de CI como **Jenkins**, **Azure**, ou **GitLab CI** para garantir que a aplicação seja continuamente integrada. Toda vez que o código for enviado para o repositório, ele seria automaticamente compilado, testado e validado para garantir que nenhuma quebra aconteça na aplicação.

- **Entrega Contínua (CD)**:
  Configuraria uma pipeline de CD para que, após a integração e testes, a aplicação fosse automaticamente implantada em um ambiente de staging ou produção. Ferramentas como **Docker** para criar containers e **Kubernetes** para orquestração de deploys garantiriam que a aplicação seja distribuída de maneira eficiente.

- **Testes Automatizados**:
  Testes unitários e de integração seriam executados em cada commit para garantir a qualidade do código. Ferramentas como **JUnit** e **Mockito** ajudariam a garantir que novas alterações não quebrem funcionalidades existentes.

Exemplo de CI/CD:
1. Um desenvolvedor realiza uma alteração no código e faz um commit.
2. O sistema de CI dispara o pipeline de integração contínua, rodando os testes automatizados e verificando se o código está íntegro.
3. Se os testes passarem, o pipeline de CD gera a imagem Docker e envia para um registry.
4. O Kubernetes ou outra ferramenta de orquestração aplica a nova versão em produção ou em staging automaticamente, garantindo que a nova versão esteja disponível para os usuários.

**Pergunta 3: Cite as ferramentas que você usaria para automatizar o processo de deploy e monitoramento da aplicação.**

Para automatizar o processo de deploy e monitoramento da aplicação, eu utilizaria o Azure, uma vez que tenho maior experiência com essa plataforma. O Azure oferece ferramentas robustas para CI/CD, como o Azure DevOps para pipelines automatizados, além de recursos para monitoramento e alertas, como o Azure Monitor e o Application Insights, que permitem monitorar a performance da aplicação e detectar possíveis problemas em tempo real.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
