
/// "user" "byUsername"
function (doc) {
    if (doc.ctype == "user") {
        emit (doc.username, doc);
    }
}