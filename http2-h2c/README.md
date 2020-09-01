This application has http2 enabled and demonstrates an upgrade request with h2c never gets a response

To reproduce:

curl -i --http2 localhost:8080

```
HTTP/1.1 101 Switching Protocols
connection: upgrade
upgrade: h2c
```

The response hangs
