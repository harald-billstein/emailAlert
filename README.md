# Email Alerter - Tailer

Email alerter connects to a socket server (separate repo), retrieves information. As a user you can subscribe to the information flow and add your personal triggerwords. If a triggerword is detected an email will be sent, Alerting you.


### Prerequisites

- [Maven](https://maven.apache.org/download.cgi)<br> 
- [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)


### Installing and Launch

`sh build.sh`

### API functions

**Start subscription:**<br>
`Method: PUT`<br>
`url /v1/words`<br>
`Body:`<br>
`{` <br>
&nbsp;&nbsp;`"emailAddress":"emailAddress",`<br>
&nbsp;&nbsp;`"words":["WARN","ERROR"]`<br>
`}`

**End  subscription:**<br>
`Method: DEL`<br>
`url /v1/words`<br>
`Params:`<br>
&nbsp;&nbsp;`email : emailAddress`<br>

**Add watchword:**<br>
`Method: PUT`<br>
`url: /v1/addresses/`<br>
`Params:`<br>
&nbsp;&nbsp;`email : emailaddress`<br>
&nbsp;&nbsp;`word : watchword`

**Remove watchword:**<br>
`Method: DEL`<br>
`url: /v1/addresses/`<br>
`Params:`<br>
&nbsp;&nbsp;`email : emailaddress`<br>
&nbsp;&nbsp;`word : watchword`