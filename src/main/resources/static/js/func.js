Testing();
function Testing(){
    const tmpDoc = document.body.innerHTML;
    var doc = tmpDoc.replace(/<[^>]*>?/g, '');
    var regExp = new RegExp("--->","gm");
    doc = doc.replace(regExp, "").trim();
    console.log(doc);
}
