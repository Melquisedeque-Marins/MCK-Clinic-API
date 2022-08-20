# MCK-Clinic
<a id="Sobre"></a>
## API REST desenvolvida em Java e SpringBoot para uma plataforma de agendamento de consultas e gerenciamento de pacientes destinada à clínicas médicas.

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

<a id="Indice"></a>
## Índice
<!--ts-->
* [Sobre](#Sobre)
* [Índice](#Indice)
* [Funcionalidades](#Funcionalidades)
* [Tecnologias utilizadas](#Tecnologias)
* [Acesso ao projeto](#Acesso_ao_projeto)
* [Pré-requisitos](#Pre-requisitos)
* [Instruções para download e execução do projeto](#Instruções)
* [Autor](#Autor)
<!--te-->

<a id="Funcionalidades"></a>
## 🔨 Funcionalidades 
- Segurança com autenticação e autorização utilizando token JWT utilizando protocolo Oauth2;
- Cadastro de usuarios;
- Validação de cadastro unico por cpf;
- Agendamento de consultas;
- Gerenciamento de médicos e especialidades;
- Validação dos agendamentos por horário e data do paciente e médico;
- Histórico de consultas por paciente;
- Confirmação da consulta pelo operador do sistema;
- Envio de notificação de consulta agendada;

<a id="Tecnologias"></a>
## 🛠️️ Tecnologias utilizadas

- Java 17
- Spring Boot 2.4.4
- PostgreSQL
- Postman
- Heroku CLI
- Docker
- InteliJ IDEA

<a id="Acesso_ao_projeto"></a>
## 📁 Acesso ao projeto

Você pode acessar os arquivos do projeto clicando [aqui](https://github.com/Melquisedeque-Marins/MCK-Clinic/tree/main/src).

<a id="Pre-requisitos"></a>
## ✔ Pré-requisitos

✔️ **JDK 17** 

✔️ **Acesso a internet**

<a id="Instruções"></a>
## 🎲️ Instruções para download e execução do projeto

- Abra o terminal e navegue até o diretório onde deseja salvar o projeto
``$ cd/"caminho do diretório"``.
- Faça o clone o repósitório
``$ git clone https://github.com/Melquisedeque-Marins/MCK-Clinic.git``
- Acesse a pasta do projeto trminal/cmd
``$ cd /MCK-Clinic``.
- Acesse a pasta do projeto trminal/cmd
``$ cd /MCK-Clinic``.
- Execute o seguinte comando caso não tenha o maven instalado
``$ ./mvnw clean package spring-boot:run``.
- Caso contrário
``$ mvn clean package spring-boot:run``.
- O servidor Toncat do projeto será inicializado na porta
``8080``
**do seu computador.

**Para Facilitar a utilização desta API segue a collection do Postman com os endpoints disponíveis.**

<a id="Autor"></a>
## Autor
<a href="https://github.com/Melquisedeque-Marins">
 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/93653645?v=4" border-radius=50%, width="100px;" alt=""/>
<br />
 <sub><b>Melquisedeque Marins Junior</b></sub></a> <a href="https://www.linkedin.com/in/melquisedeque-marins-junior-324291230"></a>

[![Linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/melquisedeque-marins-junior-324291230)

