$version: "2.0"

namespace com.example

@pattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")
@length(min: 36, max: 36)
string Uuid

@pattern("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$")
@length(min: 3, max: 36)
string UserName

// From OWASP Validation regexes: https://owasp.org/www-community/OWASP_Validation_Regex_Repository
@pattern("^[a-zA-Z0-9 .-]+$")
@length(min: 3, max: 600)
string SafeComment

// From OWASP Validation regexes: https://owasp.org/www-community/OWASP_Validation_Regex_Repository
@pattern("^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$")
@length(min: 4, max: 68)
string ReasonableDomainName

timestamp CreationDate