function Testing() {
    const tmpDoc = document.body.innerHTML;
    var doc = tmpDoc.replace(/<[^>]*>?/g, '');
    var regExp = new RegExp("--->", "gm");
    doc = doc.replace(regExp, "").trim();
    console.log(doc);
};
function getSubject(subject) {
    var tmp = document.querySelector("#subjectName");
    var tmpText = document.createTextNode(tmp.value);
    console.log(tmpText);
    console.log(subject);
};
