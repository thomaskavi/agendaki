---

# Agendaki

Sistema de agendamento online desenvolvido em **Spring Boot**, permitindo que profissionais ofereçam serviços e usuários agendem horários de forma simples e segura.

---

## 1. Resumo do Projeto

- **Nome do sistema:** Agendaki
- **Objetivo:** Facilitar o agendamento de serviços entre profissionais e clientes.
- **Tecnologias principais:**  
  - **Java 21**
  - **Spring Boot**
  - **Spring Web**
  - **Spring Security**
  - **JWT (JSON Web Token)**
  - **PostgreSQL** (produção)
  - **H2** (testes)
  -**Postgre** (dev)
  - **JPA/Hibernate**
- **Testes:**  
  - **JUnit**
  - **Mockito**
  - **RestAssured**
  - **JaCoCo**

---

## 2. Funcionalidades Implementadas

- **Cadastro de usuários:** perfis `CLIENT` e `PROFESSIONAL`
- **Autenticação:** via JWT
- **Atualização de dados pessoais:** endpoint `/users/update-me`
- **Gerenciamento de serviços:** cadastro e edição por profissionais
- **Agendamento de serviços:** clientes autenticados podem agendar horários
- **Controle de status:** aceitação, conclusão e cancelamento de agendamentos com regras de integridade
- **Regras de acesso e validações:**  
  - Não permitir edição de agendamentos com status `COMPLETED`
  - Não permitir agendar em uma data passada
  - Restrições de acesso baseadas em perfil e propriedade

---

## 3. Modelagem de Status dos Agendamentos

| Status      | Descrição                                 |
|-------------|-------------------------------------------|
| PENDING     | Aguardando aceitação pelo profissional    |
| CONFIRMED   | Aceito pelo profissional                  |
| COMPLETED   | Serviço finalizado                        |
| CANCELLED   | Cancelado por cliente ou profissional     |

---

## 4. Endpoints

### 📌 Auth

- **POST `/auth/login`**  
  - Login e geração de token JWT

---

### 👤 Usuários

- **POST `/client-signup`**  
  - Cadastro de cliente  
  - Exemplo de payload:
    ```json
    {
      "name": "João",
      "email": "joao@email.com",
      "phone": "999999999",
      "password": "123456"
    }
    ```
- **POST `/professional-signup`**  
  - Cadastro de profissional  
  - Exemplo de payload:
    ```json
    {
      "name": "Maria",
      "email": "maria@email.com",
      "phone": "999999999",
      "password": "123456",
      "slug": "maria-adv",
      "profession": "Advogada"
    }
    ```
- **PUT `/users/update-me`**  
  - Atualização de dados do usuário logado

---

### 🛠️ Serviços

- **POST `/services`**  
  - Cadastrar serviço (somente profissional)
- **PUT `/services/{id}`**  
  - Atualizar serviço (somente profissional dono)
- **DELETE `/services/{id}`**  
  - Deletar serviço (somente profissional dono)
- **GET `/professionals/{slug}/services`**  
  - Listar serviços públicos por slug

---

### 📅 Agendamentos

- **POST `/appointments`**  
  - Criar agendamento (usuário autenticado)  
  - Exemplo de payload:
    ```json
    {
      "serviceOfferedId": 1,
      "dateTime": "2025-06-15T14:00:00"
    }
    ```
- **GET `/appointments`**  
  - Listar agendamentos do usuário logado
- **PUT `/appointments/{id}`**  
  - Atualizar data do agendamento (se não `COMPLETED`)
- **PATCH `/appointments/{id}/accept`**  
  - Aceitar agendamento (profissional dono)
- **PATCH `/appointments/{id}/complete`**  
  - Marcar como concluído (profissional dono)
- **PATCH `/appointments/{id}/cancel`**  
  - Cancelar (cliente ou profissional)

---

## 5. Validações Importantes

- **Apenas profissionais** podem aceitar, completar ou alterar serviços/agendamentos que são deles.
- **Agendamentos não podem ser criados ou remarcados** para horários no passado.
- **Status `COMPLETED` é imutável:** agendamento não pode ser alterado após ser finalizado.

---

## 6. Perfis e Ambientes

- **`dev` profile:** usa **PostgreSQL**
- **`test` profile:** usa **H2 in-memory**

---

## 7. Testes

- **JUnit:** testes unitários
- **Mockito:** mocks de serviços e autenticação
- **RestAssured:** testes de API REST
- **JaCoCo:** análise de cobertura de código

---

## 8. Build e Execução

- **Compile:**  
  ```bash
  ./mvnw clean package
  ```
- **Execute localmente:**  
  ```bash
  ./mvnw spring-boot:run
  ```
- **Teste com H2 (perfil `test`):**  
  ```bash
  ./mvnw test
  ```

---

## 9. Futuras Melhorias (opcional)

- **Envio de notificações por e-mail**
- **Interface Web ou Mobile**
- **Confirmação de agendamento por link**
- **Reagendamento com histórico**

---
