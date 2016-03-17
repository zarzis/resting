# Introduction #

`RestingBuilder` gives you a lot of flexibility and yields minimalistic clean code. Use this builder when you need to set configuration options other than default. `RestingBuilder` is best used by creating it, invoking it's various configuration methods and finally calling build().


# Sample Code #

**Use build() to get list of entities**
```
  List<Product> products = new RestingBuilder("http://local.myapis.com/HellosService/V1/productInput",Product.class)
      .setPort(8080)
      .setVerb(Verb.POST)
      .setTransformationType(TransformationType.XML)
      .setConnectionTimeout(3000)
      .build();
```

**Use invoke() to get the raw response**
```
  ServiceResponse response = new RestingBuilder("http://local.myapis.com/HellosService/V1/productInput")
      .setPort(8080)
      .setVerb(Verb.POST)
      .setTransformationType(TransformationType.XML)
      .setConnectionTimeout(3000)
      .invoke();
```
# Default configurations for `RestingBuilder` #
Using `RestingBuilder`, coding is minimal. `RestingBuilder` assumes a lot of default parameters, such as,
```
verb=Verb.GET
port=80
encodingType=EncodingTypes.UTF8
transformationType=TransformationType.JSON
connectionTimeout=infinite
```
Only when non-default parameters are used, they will have to be explicitly set in the builder.
# Sample Code with default configurations #

```
  List<Product> products = new RestingBuilder("http://local.myapis.com/HellosService/V1/productInput",Product.class)
      .build();

In this case: 
Port for the service endpoint is 80.
Response is in JSON.
This is a GET service.
```
