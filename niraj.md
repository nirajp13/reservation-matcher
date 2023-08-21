## Next Steps

- Only unambiguous strict matching on confirmation codes has been implemented. So either extend existing endpoint or build new one for fuzzy matching based off guest details
  - Only caching confirmation codes in memory, need to extend this
- Current mechanism to trigger streaming is clunky & manual, so should find some automated or scheduled too
  - Following that, what if the upstream reservation gRPC service has an outage, how can this recover & self-heal?
- Not sanitising reservations from the gRPC service, should do that to ensure only valid reservations are stored
- At the moment, matching is all in one function. As part of fuzzy matching, worth extracting an interface and testing each matching algorithm separately
  - Also return the whole reservation instead of just the ID


## Commands

### HTTP endpoints
*Assuming [HTTPie](https://httpie.io/) installed*

To trigger streaming:
- `$ http localhost:9000/http/manual`

To fetch cached reservations:
- `$ http localhost:9000/http/reservations`

To fetch just one cached reservation:
- `$ http localhost:9000/http/reservations | jq '.[0]'`

### gRPC endpoints
*Assuming [gRPCurl](https://github.com/fullstorydev/grpcurl) installed*

To call checkin API
- `$ grpcurl -plaintext -d '{"confirmation_number": "does-not-exist"}' 127.0.0.1:9001 shackle.checkin.CheckinService.checkin`
- `$ grpcurl -plaintext -d '{"confirmation_number": "74BC-328Q"}' 127.0.0.1:9001 shackle.checkin.CheckinService.checkin`
