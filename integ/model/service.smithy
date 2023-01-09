$version: "2.0"

namespace com.example

use com.example.resources#TestResource
use com.example.operations#TestOperation

service TestService {
    version: "2006-03-01"
    resources: [TestResource]
    operations: [TestOperation]
}
