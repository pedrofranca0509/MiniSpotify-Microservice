 const form = document.querySelector("form");

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const email = document.getElementById("email").value;
  const senha = document.getElementById("senha").value;

  const res = await fetch("/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, senha })
  });

  if (res.ok) {
    const { token } = await res.json();
    localStorage.setItem("token", token);
    window.location.href = "/index.html";
  } else {
    alert("Usuário ou senha inválidos.");
  }
});
