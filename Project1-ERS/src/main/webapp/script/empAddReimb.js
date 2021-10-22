const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get("id");

window.onload = async () => {
    const sessionResponse = await fetch(`${domain}/api/check-session`);
    const sessionUserData = await sessionResponse.json();

    if (sessionUserData.data) {
        if (sessionUserData.data.userId != userId) {
            window.location = `${domain}/`;
        }
    } else {
        window.location = `${domain}/`;
    }
}

let returnTicketBtn = document.getElementById("return-ticket-btn");
returnTicketBtn.onclick = async () => {
    window.location = `${domain}/emp-dashboard?id=${userId}`;
}

let logoutBtn = document.getElementById("logout-btn");
logoutBtn.onclick = async () => {
    let logoutRes = await fetch(`${domain}/api/logout`);
    let logoutResData = await logoutRes.json();
    if (logoutResData.success) window.location = `${domain}/`;
}

let reimbForm = document.getElementById("reimb-form");
reimbForm.onsubmit = async (event) => {
    event.preventDefault();
    let errorMessage = document.getElementById("error-message");
    let reimbAmount = document.getElementById("reimb-amount").value;
    let reimbDescription = document.getElementById("reimb-description").value;
    let reimbType = document.getElementById("reimb-type").value;

    if (!reimbAmount) {
        errorMessage.innerHTML = "Enter an amount for your reimbursement";
    }
    if (reimbType == "Choose...") {
        errorMessage.innerHTML = "Please select a type";
    }
    if (reimbAmount && reimbType != "Choose...") {
        let addReimbResponse = await fetch(`${domain}/api/tickets`, {
            method: "POST",
            body: JSON.stringify({
                "amount": reimbAmount,
                "description": reimbDescription,
                "author": userId,
                "typeId": reimbType
            })
        });
        let addReimbRespData = await addReimbResponse.json();
        if(addReimbRespData.success) {
            errorMessage.innerHTML = '';
            window.location = `${domain}/emp-dashboard?id=${userId}`;
        }
    }
}
