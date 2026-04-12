# 🏗️ Microservices Sandbox: RESTful Architecture & Java 

Este projeto representa um **Sandbox de Aprendizado** focado na construção de APIs RESTful utilizando Java e o ecossistema Spring Boot. Ele serve como base conceitual e prática (pré-requisito) para o desenvolvimento de arquiteturas de Microsserviços, explorando padrões de projeto, persistência de dados e tratamento global de erros.

---

## Arquitetura do Sistema

A aplicação implementa o padrão de **Arquitetura em Camadas (N-Tier)**, garantindo o desacoplamento e a separação de responsabilidades (Single Responsibility Principle).



### 1. Camada de Exposição (Web/Controller)
Responsável por gerenciar os endpoints HTTP e realizar o *binding* dos dados de entrada.

* **`PersonController.java`**: Centraliza as operações de CRUD da entidade `Person`. Utiliza verbos HTTP semânticos (`GET`, `POST`, `PUT`, `DELETE`).
    * `@RestController`: Define a classe como um componente capaz de processar requisições REST.
    * `@RequestMapping("/person")`: Define o path base para versionamento e organização de rotas.
* **`TesteLogController.java`**: Implementação demonstrativa de níveis de log (SLF4J), essencial para a observabilidade em microsserviços.

### 2. Camada de Negócio (Service)
Onde reside a inteligência da aplicação e as regras de domínio.

* **`PersonServices.java`**: Atua como um orquestrador entre o Controller e o Repository.
    * **Injeção de Dependência**: Utiliza injeção via construtor (melhor prática) para o `PersonRepository`.
    * **Tratamento de Fluxo**: Valida a existência de registros e dispara exceções de negócio como a `ResourceNotFoundException`.

### 3. Camada de Acesso a Dados (Persistence/Repository)
Abstrai a complexidade das consultas SQL através de ORM (Object-Relational Mapping).

* **`PersonRepository.java`**: Interface que estende `JpaRepository`. Graças ao Spring Data JPA, operações de banco de dados são geradas dinamicamente em tempo de execução.
* **`Person.java` (Model)**: Entidade mapeada para o banco de dados via JPA.
    * `@Entity` & `@Table`: Mapeiam a classe para a tabela física `person`.
    * `@Column`: Define restrições como `nullable`, `length` e nomes específicos de colunas.

### 4. Camada de Infraestrutura e Erros (Cross-cutting)
Funcionalidades que atravessam todas as camadas do sistema.

* **`CustomEntityResponseHandler.java`**: Implementa o padrão **Global Exception Handler** com `@ControllerAdvice`.
    * Captura exceções em qualquer ponto da stack e as traduz em um objeto `ExceptionResponse` padronizado.
* **`ExceptionResponse.java`**: Utiliza **Java Records** (JDK 14+) para representar uma estrutura de erro imutável e concisa.
* **`application.yml`**: Configura o driver JDBC do MySQL, as propriedades do Hibernate (como o `ddl-auto: update`) e os níveis de log da aplicação.

---

## Anotações

| Anotação | Responsabilidade |
| :--- | :--- |
| `@SpringBootApplication` | Inicializa o contexto do Spring, autoconfiguração e scan de componentes. |
| `@RestController` | Combina `@Controller` e `@ResponseBody`, facilitando o retorno de JSON. |
| `@Service` | Especialização de `@Component`, indica que a classe detém lógica de negócio. |
| `@Autowired` | Delega ao Spring a responsabilidade de instanciar e gerenciar dependências. |
| `@ExceptionHandler` | Define o método responsável por tratar um tipo específico de erro HTTP. |
| `@CrossOrigin` | Configura o CORS, permitindo que diferentes origens consumam a API. |

---

## Fluxo de Processamento (Mapa Mental)

1.  **Request**: O cliente envia um JSON via `POST /person`.
2.  **Controller**: O `@RequestBody` converte o JSON no objeto `Person`.
3.  **Service**: O serviço recebe o objeto, gera logs de auditoria e solicita a persistência.
4.  **Repository**: O JPA traduz o objeto para um comando `INSERT INTO person...`.
5.  **Database**: O MySQL persiste o dado e retorna o ID gerado.
6.  **Response**: O objeto persistido retorna por toda a cadeia até o cliente com Status 200 OK.
7.  **Erro**: Caso o ID solicitado em um `GET` não exista, a `ResourceNotFoundException` é lançada, o `Handler` a captura e retorna um 404 Not Found estruturado.

### 5. Camada de Observabilidade (Actuator)
Responsável por monitorar a saúde da aplicação e expor métricas operacionais em tempo real, fundamental para a manutenção de microsserviços em produção.
Spring Boot Actuator: Fornece endpoints integrados que permitem "olhar para dentro" da aplicação sem a necessidade de criar rotas manuais para isso.## 📊 Glossário Técnico: Conceitos Actuator

### Elementos
| Conceito      | Descrição                                                                 |
|---------------|--------------------------------------------------------------------------|
| Health Check  | Verifica se a aplicação e suas dependências (como o MySQL) estão operacionais. |
| Metrics       | Expõe dados de performance (uso de CPU, memória, latência de requisições). |
| Info          | Exibe informações customizadas sobre a versão e o estado do build do projeto. |

### Endpoints Actuator

| Endpoint              | Função                                                                 |
|----------------------|------------------------------------------------------------------------|
| /actuator/health     | Exibe o status da aplicação (UP/DOWN) e de componentes externos.      |
| /actuator/metrics    | Lista as métricas disponíveis (ex: http.server.requests, jvm.memory.used). |
| /actuator/env        | Expõe as propriedades do ambiente e variáveis de configuração.        |
| /actuator/loggers    | Permite visualizar e alterar o nível de log (INFO, DEBUG) em tempo de execução. |
| /actuator/mappings   | Lista todos os caminhos (URI) mapeados nos @Controllers.              |


