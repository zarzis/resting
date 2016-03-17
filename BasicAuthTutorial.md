### Using basic authentication in resting ###

From release 0.8 onwards, you can handle basic authentication by using `RestingBuilder`.


#### Sample code for enabling basic authentication ####
```
  List<Product> products = new RestingBuilder("http://local.myapis.com/HellosService/V1/productInput",Product.class)
      .enableBasicAuthentication("user", "password")
      .build();
```


#### Sample code for enabling basic authentication with realm ####
```
  List<Product> products = new RestingBuilder("http://local.myapis.com/HellosService/V1/productInput",Product.class)
      .enableBasicAuthentication("user", "password","host",port,"realm")
      .build();
```