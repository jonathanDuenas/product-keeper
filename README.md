# product-keeper

## Dependencies

You can follow [this] tutorial to see a clean install of all the components needed.

## Usage

Install node dependencies with:

```
$ npm install
```

Download lein dependencies with:

```
$ lein deps
```

## Modes

Before you start the application you fist need to change the mode in which the dependencies will be compiled. To do this just do

```
$ lein web|app|amp
```

So if you want to run the web mode of the application just type in the console:

```
$ lein web
```

## Web mode

Run it as:

```
$ lein figwheel web
```

## AMP mode

Run it as:

```
$ lein ring server
```

In web and app modes the application will start itself.

## App mode

If you want to run the app mode first you need to start node manually

```
$ npm start
```

Then

```
$ lein figwheel andriod|ios
```

Once is compiled you can follow the React-native guide on how to run the project

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[this]: https://gadfly361.github.io/gadfly-blog/2016-11-13-clean-install-of-ubuntu-to-re-natal.html