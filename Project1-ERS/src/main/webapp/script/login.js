let loginForm = document.getElementById("login-form");

window.onload = async () => {
    const sessionResponse = await fetch(`${domain}/api/check-session`);
    const sessionUserData = await sessionResponse.json();
    if(sessionUserData.data) {
        if (sessionUserData.data.roleId == 1) {
            window.location = `${domain}/emp-dashboard?id=${sessionUserData.data.userId}`;
        } else {
            window.location = `${domain}/mgr-dashboard?id=${sessionUserData.data.userId}`;
        }
    }
}

loginForm.onsubmit = async (event) => {
    event.preventDefault();

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let loginResponse = await fetch(`${domain}/api/login`, {
        method: "POST",
        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    let loginResponseData = await loginResponse.json();
    let messageElem = document.getElementById("login-message");

    if(loginResponseData.success) {
        messageElem.innerText = '';
        if(loginResponseData.data.roleId == 1)
            window.location = `${domain}/emp-dashboard?id=${loginResponseData.data.userId}`;
        else if(loginResponseData.data.roleId == 2)
            window.location = `${domain}/mgr-dashboard?id=${loginResponseData.data.userId}`;
    }else {
        messageElem.style = "background-color: white";
        messageElem.innerText = loginResponseData.message;
    }
}
