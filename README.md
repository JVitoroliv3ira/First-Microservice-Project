# My First Microservice

# Sobre:
Este projeto consiste em um microservice que possui um endpoint de cadastro e um serviço de email que envia um email de boas vindas para novos usuários. O objetivo desse microservice é fornecer a funcionalidade de cadastro para outros serviços da aplicação, além de enviar um email de boas vindas para novos usuários.

O endpoint de cadastro é responsável por receber as informações do novo usuário, validá-las e persisti-las em um banco de dados. O serviço de email utiliza o Mailtrap para enviar o email de boas vindas para o endereço de email fornecido pelo usuário no momento do cadastro. O Mailtrap é uma ferramenta de teste de email que permite simular o envio de emails em ambientes de desenvolvimento, sem enviar emails reais para os usuários.

Dessa forma, o serviço de email do microservice pode ser testado em um ambiente seguro e controlado antes de ser integrado ao sistema em produção. Com isso, é possível garantir que os emails de boas vindas sejam enviados corretamente para os novos usuários sem expor a aplicação a riscos de segurança.

# Como executar:
- Para iniciar o microserviço e enviar o email de boas-vindas, siga os passos abaixo:
    
    1 - Inicie o servidor Kafka, execute o seguinte comando na raiz do projeto:
    
    ```
    docker-compose up
    ```

    2 - Compile o serviço de email, execute o seguinte comando na pasta `email-service`:

    ```
    mvn clean && mvn package
    ```

    3 - Realize o build do serviço de email, execute o seguinte comando na pasta `email-service`:

    ```
    docker build -t email-service .
    ```

    4 - Execute o serviço de email, execute o seguinte comando na pasta `email-service`:

    ```
    docker run --network=host \
           -e EMAIL_PROPERTY_EMAIL=SEU_EMAIL \
           -e EMAIL_PROPERTY_USERNAME=SEU_USUARIO \
           -e EMAIL_PROPERTY_PASSWORD=SUA_SENHA \
           email-service
    ```
    - ATENÇÂO: Substitua "SEU_EMAIL", "SEU_USUARIO" e "SUA_SENHA" pelos valores correspondentes.

    5 - Compile o serviço de autenticação, execute o seguinte comando na pasta `authentication-service`:

    ```
    mvn clean && mvn package
    ```

    6 - Realize o build do serviço de email, execute o seguinte comando na pasta `authentication-service`:

    ```
    docker build -t authentication-service .
    ```

    7 - Execute o serviço de email, execute o seguinte comando na pasta `authentication-service`:

    ```
    docker run --network=host authentication-service
    ```

    8 - Com isso, o microserviço de autenticação e envio de email de boas-vindas estará em execução. Você pode enviar uma solicitação POST para o endpoint de cadastro na URL http://localhost:8080/api/v1/auth/register com as informações do novo usuário para testar o serviço.


    # Documentação:

    #### **Endpoint de cadastro**
    - Requisição POST http://localhost:8080/api/v1/auth/register
    - O corpo da solicitação deve estar no formato JSON e incluir as seguintes informações:

    ```json
        {
            "name": "string",
            "email": "string",
            "password": "string"
        }
    ```