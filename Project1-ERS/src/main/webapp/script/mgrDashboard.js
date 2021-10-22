const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get("id");
let filterType = document.getElementById("filter-type");

window.onload = async () => {
    const sessionResponse = await fetch(`${domain}/api/check-session`);
    const sessionUserData = await sessionResponse.json();

    if(sessionUserData.data){
        if(sessionUserData.data.userId != userId) {
            window.location = `${domain}/`;
        }
    }else {
        window.location = `${domain}/`;
    }
    populateData();
}

let logoutBtn = document.getElementById("logout-btn");
logoutBtn.onclick = async () => {
    let logoutRes = await fetch(`${domain}/api/logout`);
    let logoutResData = await logoutRes.json();
    if(logoutResData.success) window.location = `${domain}/`;
}

populateData = async () => {
    let reimbRes = await fetch(`${domain}/api/mgr-tickets?statusId=${filterType.value}`);
    let reimbResData = await reimbRes.json();

    reimbResData.data.sort((a, b) => {
        if(a.reimbursementId < b.reimbursementId) return 1;
        else if (a.reimbursementId > b.reimbursementId) return -1;
        else return 0;
    });

    let cardDataContElem = document.getElementById("reimb-card");
    cardDataContElem.innerHTML = ``;

    reimbResData.data.forEach(ticket => {
        let resolvedTicket;
        if(!ticket.resolvedOn) {
            resolvedTicket = null;
        }
        if(ticket.resolvedOn) {
            resolvedTicket = new Date(ticket.resolvedOn).toDateString();
        }

        cardDataContElem.innerHTML +=
        `
        <div class="d-flex justify-content-between card-header">
            <a class="card-link text-decoration-none" data-bs-toggle="collapse" href="#cardId${ticket.reimbursementId}">
                Reimbursement Ticket Id ${ticket.reimbursementId}
            </a>
            <span">${ticket.status}</span>
        </div>
        <div id="cardId${ticket.reimbursementId}" class="collapse" data-parent="#accordion">
            <div class="card-body">
                <div class="bg-light fw-bold">Amount</div>
                <div>${ticket.amount}</div>
                <div class="bg-light fw-bold">Submitted</div>
                <div>${new Date(ticket.submittedOn).toDateString()}</div>
                <div class="bg-light fw-bold">Resolved</div>
                <div>${resolvedTicket}</div>
                <div class="bg-light fw-bold">Description</div>
                <div>${ticket.description}</div>
                <div class="bg-light fw-bold">Author</div>
                <div>${ticket.authorDetails.firstName} ${ticket.authorDetails.lastName}</div>
                <div class="bg-light fw-bold">Status</div>
                <div>${ticket.status}</div>
                <div class="bg-light fw-bold">Type</div>
                <div>${ticket.type}</div>
                <div class="row d-flex justify-content-evenly">
                    <div class="col-8 col-md-6">
                        <select id="filter-type${ticket.reimbursementId}" class="form-select">
                            <option value="1">Pending</option>
                            <option value="2">Approved</option>
                            <option value="3">Denied</option>
                        </select>
                    </div>
                    <button id="resolve-btn${ticket.reimbursementId}" class="btn btn-primary col-4 col-sm-3 col-md-2"
                    onclick="resolveFtn(${ticket.reimbursementId}) ">Resolve</button>
                </div>
            </div>
        </div>
        `
    });

    resolveFtn = async (reimbursementId) => {
        let statusType = document.getElementById(`filter-type${reimbursementId}`).value;
        console.log(statusType);
        console.log(reimbursementId);
        let resolveResponse = await fetch(`${domain}/api/resolve-ticket`, {
            method: "PUT",
            body: JSON.stringify({
                "reimbursementId": reimbursementId,
                "resolver": userId,
                "statusId": statusType
            })
        });
        resolveResponseData = await resolveResponse.json();
        if(resolveResponseData.success) populateData();
    }

    filterType.addEventListener("change", populateData);
}
