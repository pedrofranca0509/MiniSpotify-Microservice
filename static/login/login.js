async function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const res = await fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    if (res.ok) {
        const data = await res.json();
        localStorage.setItem("token", data.token);
        window.location.href = "index.html";
    } else {
        const msg = await res.text();
        document.getElementById("mensagem").textContent = "Erro ao autenticar: " + msg;
    }
}