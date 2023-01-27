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
        testId: UUID
    },
    create: CreateTest,
    delete: DeleteTest,
    read: GetTestById,
}

/// Get A Test by Id
@readonly
@http(
    method: "GET",
    uri: "/tests/{testId}"
)
operation GetTestById {
    input := {
        @required
        @httpLabel
        testId: UUID
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
    testId: UUID,

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
    uri: "/tests"
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
    test: SafeComment,

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
    testId: UUID,

    @required
    createdBy: UserName,

    @required
    test: SafeComment,

    @required
    creationDate: CreationDate,
}

@idempotent
@http(
    method: "DELETE",
    uri: "/tests/{testId}"
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
    testId: UUID
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