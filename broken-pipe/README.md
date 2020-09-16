The client fails with IOException Broken pipe when it should be an HttpClientResponseException: Unauthorized

To reproduce, run the test and observe (the JwtValidator warnings are expected):

```
14:32:38.223 [Test worker] INFO  jwt.upload.UploadTest - HttpClientResponseException: Unauthorized
14:32:38.238 [Test worker] INFO  jwt.upload.UploadTest - RuntimeException: java.io.IOException: Broken pipe
```

From my research I've found that this only happens if the client thread is reused. The first execution on a new thread works.
