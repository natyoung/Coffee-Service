[![Build Status](https://snap-ci.com/natyoung/Coffee-Service/branch/master/build_image)](https://snap-ci.com/natyoung/Coffee-Service/branch/master)

## Coffee-Service
A solution to the [March Software Shokunin Challenge](https://github.com/software-shokunin/coffee-api-challenge)

### Stuff
    MSF4J
    Redis
    Maven

### Run
    ./go run

### Modify the contracts to work with Java
    change "type": "object" to "type": "string"

### Output:
```bash
$ bundle exec rake pacto:validate['https://msf4j-march-shokunin.herokuapp.com','contracts'] --trace

** Invoke pacto:validate (first_time)
** Execute pacto:validate
Validating contracts against host https://msf4j-march-shokunin.herokuapp.com
         OK!  get-menu.json
         OK!  get-order-status.json
         OK!  order-coffee.json
3 valid contracts
```
