jbc-framework

JBC - Java Business Controller, is a Framework for Business Control Layer for projects developed in Java, its main purpose is to optimize development time that is used to design repetitive code, which in turn are very common when it comes to control transaction and persistence in database.

The JBC is used only in the business layer for projects developed in Java, as a tool for aiding layers and Control Model of the MVC development model. Its function is to control transactions between operations and achieve access to the database, and through persistence or queries. The primary basis for the development was the use of the concepts of Java Reflection and Generics, ie generic programming.

To work persistently in the database was used Hibernate Framework, which through the concepts of HQL and Criteria, helped in encapsulation and manipulation of objects in the database, but also made ​​the JBC independent database, or that is, an item left to the choice of developer. Finally, to connect to the Database, we used the JDBC API.

As the JBC Framework is an implementation of the business layer, it can be used for any type of implementation of the concept MVC View, ie it is independent interface presentation to the customer, can be used in applications J2SE and J2EE J2ME , with applications Desktop, Web and Mobile.

@ author: Rodrigo Leandro Marconato