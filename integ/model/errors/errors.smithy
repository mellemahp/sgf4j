$version: "2.0"

namespace com.example

use com.example#Uuid
use com.example#SafeComment

@error("client")
@httpError(404)
structure NotFoundError {
    @required
    commentId: Uuid
}

@error("server")
@httpError(500)
structure InternalError {
    @required
    message: SafeComment
}
