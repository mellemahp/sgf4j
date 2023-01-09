$version: "2.0"

namespace com.example.resources

use com.example#NotFoundError
use com.example#InternalError
use com.example#SafeComment
use com.example#Uuid
use com.example#UserName
use com.example#CreationDate

resource TestResource {
    identifiers: {
        TestId: Uuid
    },
    create: CreateTest,
    delete: DeleteTest,
    read: GetTestById,
}

/// Get A Test by Id
@readonly
@http(
    method: "GET",
    uri: "/Tests/{TestId}"
)
operation GetTestById {
    input := {
        @required
        @httpLabel
        TestId: Uuid
    },
    output: GetTestByIdOutput,
    errors: [
        NotFoundError,
        InternalError
    ]
}

@output
structure GetTestByIdOutput {
    @required
    TestId: Uuid,

    @required
    createdBy: UserName,

    @required
    Test: SafeComment,

    @required
    creationDate: CreationDate,
}

@http(
    method: "POST",
    uri: "/Tests"
)
operation CreateTest {
    input: CreateTestInput,
    output: CreateTestOutput,
    errors: [
        NotFoundError,
        InternalError
    ]
}

@input
structure CreateTestInput{
    @required
    Test: SafeComment,

    value: SafeComment = "Test",

    users: MyMap
}

map MyMap {
    key: Uuid,
    value: Integer
}

@output
structure CreateTestOutput {
    @required
    TestId: Uuid,

    @required
    createdBy: UserName,

    @required
    Test: SafeComment,

    @required
    creationDate: CreationDate,
}

@idempotent
@http(
    method: "DELETE",
    uri: "/Tests/{TestId}"
)
operation DeleteTest {
    input: DeleteTestInput,
    output: DeleteTestOutput,
    errors: [
        NotFoundError,
        InternalError
    ]
}

@input
structure DeleteTestInput{
    @required
    @httpLabel
    TestId: Uuid
}

@output
structure DeleteTestOutput {
    @required
    commentId: Uuid,

    test: TestEnum
}

intEnum TestEnum {
    DEFINED = 1,
    UNDEFINED = 4
}