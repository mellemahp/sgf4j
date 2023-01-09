$version: "2.0"

namespace com.example.operations

use com.example#NotFoundError
use com.example#InternalError

use com.example#Uuid

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
    TestId: Uuid
}

@output
structure TestOperationOutput {
    @required
    commentId: Uuid,

    test: TestEnum
}

intEnum TestEnum {
    DEFINED = 1,
    UNDEFINED = 4
}