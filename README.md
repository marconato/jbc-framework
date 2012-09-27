jbc-framework

JBC - Java Business Controller, é um Framework de Controle da camada de Negócio para projetos desenvolvidos em Java, seu principal objetivo é otimizar o tempo de desenvolvimento que é usado para projetar códigos repetitivos, que por sua vez são muito frequentes quando se trata de controle de transação e persistência em banco de dados.

O JBC é utilizado somente na camada de negócio para projetos desenvolvidos em Java, auxiliando como um utilitário para as camadas de Model e Control do modelo de desenvolvimento MVC. Sua função é fazer o controle de transações entre as operações e realizar o acesso ao banco de dados, sendo através de persistência ou consultas. A base principal para o seu desenvolvimento, foi a utilização dos conceitos Java de Generics e Reflection, ou seja, programação genérica.

Para trabalhar com persistência em banco de dados, foi utilizado o Framework Hibernate, que através dos conceitos de Criteria e HQL, auxiliaram no encapsulamento e na manipulação dos objetos com a base de dados, como também tornaram o JBC independente de banco de dados, ou seja, um item deixado para a escolha do desenvolvedor. Por fim, para se conectar com o Database, foi utilizado a API JDBC.

Como o JBC é um Framework de implementação da camada de negócio, ele pode ser usado para qualquer tipo de implementação View do conceito MVC, ou seja, é independente de interface de apresentação com o cliente, pode ser utilizado em aplicações J2SE, J2ME e J2EE, sendo aplicações Desktop, Web e Mobile.

JBC é um Framework open-source

@autor: Rodrigo Leandro Marconato

Siga o blog JBC: http://jbc-framework.blogspot.com.br/