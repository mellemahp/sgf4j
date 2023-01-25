$version: "2.0"

namespace com.example.resources

use com.example#NotFoundError
use com.example#InternalError
use com.example#SafeComment
use com.hmellema.sgf4j.extensions#FriendlyUUID
use com.hmellema.sgf4j.extensions#UUID
use com.example#UserName
use com.example#CreationDate

resource TestResource {
    identifiers: {
        TestId: UUID
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
        TestId: UUID
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
    TestId: UUID,

    @required
    createdBy: UserName,

    @required
    test: MyStruct,

    @required
    creationDate: CreationDate,
}

union MyStruct {
    comment: SafeComment,
    other: UUID
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
    key: UUID,
    value: Integer
}

@output
structure CreateTestOutput {
    @required
    TestId: UUID,

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
    TestId: UUID
}

@output
structure DeleteTestOutput {
    @required
    commentId: UUID,

    test: TestEnum
}

intEnum TestEnum {
    DEFINED = 1,
    UNDEFINED = 4
}