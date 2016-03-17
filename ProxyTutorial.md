### Using proxy in resting ###

From release 0.8 onwards, you can handle proxy by using `RestingBuilder`.


#### Sample code for handling proxy ####
```
  List<Product> products = new RestingBuilder("http://local.myapis.com/HellosService/V1/productInput",Product.class)
      .setProxy("proxyhost", proxyport)
      .build();
```

#### Sample code for handling proxy with authentication ####

```
  List<Product> products = new RestingBuilder("http://local.myapis.com/HellosService/V1/productInput",Product.class)
      .setProxy("proxyhost", proxyport, "proxyuser","proxypassword")
     .build();
```