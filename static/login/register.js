const form = document.querySelector("form");

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const nome = document.getElementById("nome").value;
  const email = document.getElementById("email").value;
  const senha = document.getElementById("senha").value;

  const res = await fetch("/usuarios", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome, email, senha })
  });

  if (res.ok) {
    alert("Usuário criado com sucesso! Faça login.");
    window.location.href = "/login/login.html";
  } else {
    const text = await res.text();
    alert("Erro ao registrar: " + text);
  }
});
