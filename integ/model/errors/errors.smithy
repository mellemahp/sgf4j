$version: "2.0"

namespace com.example

use com.example#SafeComment
use com.hmellema.sgf4j.extensions#UUID


@error("client")
@httpError(404)
structure NotFoundError {
    @required
    commentId: UUID
}

@error("server")
@httpError(500)
structure InternalError {
    @required
    other: SafeComment
}
