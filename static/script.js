
const token = localStorage.getItem("token");
if (!token) {
  window.location.href = "/login/login.html";
}

const audio = document.getElementById("audio");
const btnPlayPause = document.getElementById("play");   
const btnNext = document.getElementById("next");         
const btnPrev = document.getElementById("prev");         

let playlists = [];
let currentPlaylist = null;
let currentTrackIndex = 0;


window.addEventListener("DOMContentLoaded", async () => {
  await carregarPlaylists();
});


btnPlayPause.addEventListener("click", () => {
  if (audio.paused) {
    audio.play();
    btnPlayPause.textContent = "⏸️"; 
  } else {
    audio.pause();
    btnPlayPause.textContent = "▶️"; 
  }
});


btnNext.addEventListener("click", () => {
  if (!currentPlaylist) return;
  currentTrackIndex = (currentTrackIndex + 1) % currentPlaylist.musicas.length;
  tocarMusicaAtual();
});


btnPrev.addEventListener("click", () => {
  if (!currentPlaylist) return;
  currentTrackIndex = (currentTrackIndex - 1 + currentPlaylist.musicas.length) % currentPlaylist.musicas.length;
  tocarMusicaAtual();
});


document.getElementById("btn-criarPlaylist").addEventListener("click", async () => {
  const nome = document.getElementById("novaPlaylistNome").value;
  if (!nome) return alert("Informe o nome da playlist");

  await fetch("/playlists", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ nome }),
  });

  await carregarPlaylists();
});


document.getElementById("btn-adicionarMusica").addEventListener("click", async () => {
  const url = document.getElementById("musicaUrl").value;
  const titulo = document.getElementById("musicaTitulo").value;
  const playlistId = document.getElementById("selectPlaylists").value;

  if (!url || !titulo || !playlistId) {
    alert("Preencha todos os campos");
    return;
  }

  await fetch(`/playlists/${playlistId}/musicas`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ titulo, url }),
  });

  await carregarPlaylists();
});


async function carregarPlaylists() {
  const res = await fetch("/playlists", {
    headers: { Authorization: `Bearer ${token}` },
  });

  playlists = await res.json();

  const ul = document.getElementById("playlist-list");
  const select = document.getElementById("selectPlaylists");

  ul.innerHTML = "";
  select.innerHTML = "";

  playlists.forEach((playlist) => {
    
    const option = document.createElement("option");
    option.value = playlist.id;
    option.textContent = playlist.nome;
    select.appendChild(option);

    
    const li = document.createElement("li");
    li.textContent = playlist.nome;

    const btnEntrar = document.createElement("button");
    btnEntrar.textContent = "▶️ Entrar";
    btnEntrar.onclick = () => {
      currentPlaylist = playlist;
      currentTrackIndex = 0;
      tocarMusicaAtual();
    };

    const btnRemover = document.createElement("button");
    btnRemover.textContent = "🗑️ Remover";
    btnRemover.onclick = async () => {
      await fetch(`/playlists/${playlist.id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });
      await carregarPlaylists();
    };

    li.appendChild(btnEntrar);
    li.appendChild(btnRemover);
    ul.appendChild(li);
  });
}

function tocarMusicaAtual() {
  if (!currentPlaylist || currentPlaylist.musicas.length === 0) return;
  const musica = currentPlaylist.musicas[currentTrackIndex];
  audio.src = musica.url;
  audio.play();
  btnPlayPause.textContent = "⏸️"; 
}
