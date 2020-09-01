To reproduce:

wrk -H 'Accept: text/plain,text/html;q=0.9,application/xhtml+xml;q=0.9,application/xml;q=0.8,*/*;q=0.7' -H 'Connection: keep-alive' --latency -d 2 -c 1 --timeout 8 -t 1 http://localhost:8080/plaintext -s /Users/jameskleeh/IdeaProjects/FrameworkBenchmarks/toolset/wrk/pipeline.lua -- 2

That will use HTTP 1.1 pipelining. The reason the latency is displayed as 0 is because 1 of the requests

I have researched the issue and believe its related to line 153 of AbstractNioByteChannel which closes the channel if the bytes read is < 0

`close = allocHandle.lastBytesRead() < 0;`
