  async function login(event) {
  event.preventDefault();

  const email = document.querySelector("#email").value;
  const senha = document.querySelector("#senha").value;

  if (!email || !senha) {
    alert("Preencha todos os campos!");
    return;
  }

  try {
    const res = await fetch("/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, senha }),
    });

    if (res.ok) {
      const data = await res.json();
      localStorage.setItem("token", data.token);
      window.location.href = "../index.html";
    } else {
      const msg = await res.text();
      alert("Login inválido: " + msg);
    }
  } catch (error) {
    alert("Erro na conexão com o servidor.");
    console.error(error);
  }
}
