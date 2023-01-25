$version: "2.0"

namespace com.example.operations

use com.example#NotFoundError
use com.hmellema.sgf4j.extensions#UUID
use com.example#InternalError


@http(
    method: "POST",
    uri: "/TestOperation/{TestId}"
)
operation TestOperation {
    input: TestOperationInput,
    output: TestOperationOutput,
    errors: [
        NotFoundError,
        InternalError
    ]
}

@input
structure TestOperationInput {
    @required
    @httpLabel
    TestId: UUID
}

@output
structure TestOperationOutput {
    @required
    commentId: UUID,

    test: TestEnum
}

intEnum TestEnum {
    DEFINED = 1,
    UNDEFINED = 4
}