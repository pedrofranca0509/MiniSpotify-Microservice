document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const nome = document.getElementById("nome").value;
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    try {
        const response = await fetch("/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome, email, senha })
        });
        if (response.ok){
            alert("Usuário registrado com sucesso!");
            window.location.href = "/login.html";
        }else {
            const errorData = await response.json();
            alert(`Erro ao registrar usuário: ${errorData.message}`);
        }
    } catch (error) {
        console.error("Erro ao registrar usuário:", error);
        alert("Erro ao registrar usuário. Tente novamente mais tarde.");
    }
});  