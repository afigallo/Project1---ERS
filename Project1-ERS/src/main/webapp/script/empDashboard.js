const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get("id");

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

let addTicketBtn = document.getElementById("add-ticket-btn");
addTicketBtn.onclick = async () => {
    window.location = `${domain}/emp-add-reimb?id=${userId}`;
}

let logoutBtn = document.getElementById("logout-btn");
logoutBtn.onclick = async () => {
    let logoutRes = await fetch(`${domain}/api/logout`);
    let logoutResData = await logoutRes.json();
    if(logoutResData.success) window.location = `${domain}/`;
}

populateData = async () => {
    let reimbRes = await fetch(`${domain}/api/tickets?id=${userId}`);
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
        if(!ticket.resolverDetails) {
            resolverDetails = null;
        }
        if(ticket.resolverDetails) {
            resolverDetails = ticket.resolverDetails.firstName + " " + ticket.resolverDetails.lastName
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
                <div class="bg-light fw-bold">Resolver</div>
                <div>${resolverDetails}</div>
                <div class="bg-light fw-bold">Status</div>
                <div>${ticket.status}</div>
                <div class="bg-light fw-bold">Type</div>
                <div>${ticket.type}</div>
            </div>
        </div>
        `
    });
}
