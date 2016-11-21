# newsfeed-jsf-pf
News Feed web page with JSF and PrimeFaces

To build the application run

```
    mvn package
```

Once built, you may deploy produced war to application server. For example, to deploy on GlassFish (which is up and running), run

```
	asadmin deploy target/newsfeed
```

The application can be accessed at http://localhost:8080/newsfeed/public/newsfeed.xhtml

**Note:** I have deployed the application to GlassFish 4.1.1, and on accessing aforementioned page I have encountered [this](https://bugs.eclipse.org/bugs/show_bug.cgi?id=463169#c13) problem.
 I have successfully resolved it with solution given in comment #3 on the same page.
