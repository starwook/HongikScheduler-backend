function Testing() {
    const tmpDoc = document.body.innerHTML;
    var doc = tmpDoc.replace(/<[^>]*>?/g, '');
    var regExp = new RegExp("--->", "gm");
    doc = doc
        .replace(regExp, "")
        .trim();
    console.log(doc);
};

function objToString (obj) {
    var str = '';
    for (var p in obj) {
        if (obj.hasOwnProperty(p)) {
            str += p + '::' + obj[p] + '\n';
        }
    }
    return str;
}
function strChange(str){
    var str1 =str.slice(1,2);
    var str2 =str.slice(5);
    return str2+str1+"학년";

}
function getSubject(subjectList) {
    let list = document.getElementById("itemList");
    while(list.hasChildNodes()){
        list.removeChild(list.firstChild);
    }
    let tmp = document.getElementById("subjectName").value.toLowerCase();
    // var searchText = objToString(document.createTextNode(tmp.value));
   /*  document.write("검색어"+tmp);*/
    var itemList = document.querySelector("#itemList");
    for(let i=0;i<subjectList.length;i++){

        var subjectName = JSON.stringify(subjectList[i].subjectName);
        if(subjectName.indexOf(tmp) !=-1){
            var newList = document.createElement("li");
            var professor = JSON.stringify(subjectList[i].professor);
            var total = subjectName.concat(professor);
            console.log(total);
            console.log(professor);
            var tmpStr = Object.values(subjectList[i]).toString();
            tmpStr = strChange(tmpStr);
            console.log(tmpStr);
            newList.appendChild(document.createTextNode(tmpStr));

            var addBtn = document.createElement('a');
            addBtn.appendChild(document.createTextNode("              과목추가"));
            // addBtn.setAttribute('style','textAlign: right');
            addBtn.style.textAlign ="center";
            newList.appendChild(addBtn);

            itemList.appendChild(newList);
        }
    }



    // subjectList.forEach(function (element) {
    //     var tmpText = JSON.stringify(element.subjectName);
    //     console.log(typeof tmpText)
    //     if (tmpText.indexOf(tmp) != -1) {
    //         console.log(JSON.stringify(element.subjectName));
    //         console.log(JSON.stringify(element.professor));
    //     }
    // });
    console.log(subjectList);
};

