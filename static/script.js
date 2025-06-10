const token = localStorage.getItem("token");
if (!token) {
  window.location.href = "/login/login.html";
}

let currentPlaylist = null;
let musicaAtual = 0;
let playlists = [];

async function carregarPlaylists() {
  try {
    const res = await fetch("/api/playlists", {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (res.status === 401) {
      localStorage.removeItem("token");
      return window.location.href = "/login/login.html";
    }

    if (!res.ok) {
      alert("Erro ao carregar playlists");
      return;
    }

    playlists = await res.json();
    const playlistList = document.getElementById("playlistList");
    const playlistSelect = document.getElementById("playlistSelect");

    playlistList.innerHTML = "";
    playlistSelect.innerHTML = "";

    playlists.forEach((playlist, index) => {
      const li = document.createElement("li");
      li.innerText = playlist.nome;
      li.onclick = () => selecionarPlaylist(index);
      playlistList.appendChild(li);

      const option = document.createElement("option");
      option.value = index;
      option.innerText = playlist.nome;
      playlistSelect.appendChild(option);
    });
  } catch (error) {
    console.error("Erro:", error);
    alert("Erro de conexão com o servidor.");
  }
}

function selecionarPlaylist(index) {
  currentPlaylist = playlists[index];
  musicaAtual = 0;
  tocarMusicaAtual();
}

function tocarMusicaAtual() {
  if (!currentPlaylist || currentPlaylist.musicas.length === 0) return;

  const musica = currentPlaylist.musicas[musicaAtual];
  const player = document.getElementById("player");
  player.src = musica.link;
  player.play();

  document.getElementById("musicaAtual").innerText = `Tocando: ${musica.nome}`;
}

function proximaMusica() {
  if (!currentPlaylist || currentPlaylist.musicas.length === 0) return;

  musicaAtual = (musicaAtual + 1) % currentPlaylist.musicas.length;
  tocarMusicaAtual();
}

function musicaAnterior() {
  if (!currentPlaylist || currentPlaylist.musicas.length === 0) return;

  musicaAtual = (musicaAtual - 1 + currentPlaylist.musicas.length) % currentPlaylist.musicas.length;
  tocarMusicaAtual();
}

async function entrarPlaylist() {
  const index = document.getElementById("playlistSelect").value;
  const playlist = playlists[index];

  const res = await fetch(`/api/playlists/${playlist.id}`, {
    headers: { Authorization: `Bearer ${token}` },
  });

  if (res.status === 401) {
    localStorage.removeItem("token");
    return window.location.href = "/login/login.html";
  }

  if (!res.ok) {
    alert("Erro ao entrar na playlist");
    return;
  }

  currentPlaylist = await res.json();
  musicaAtual = 0;
  tocarMusicaAtual();
}

async function removerPlaylist() {
  const index = document.getElementById("playlistSelect").value;
  const playlist = playlists[index];

  const res = await fetch(`/api/playlists/${playlist.id}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${token}` },
  });

  if (res.status === 401) {
    localStorage.removeItem("token");
    return window.location.href = "/login/login.html";
  }

  if (!res.ok) {
    alert("Erro ao remover playlist");
    return;
  }

  alert("Playlist removida!");
  carregarPlaylists();
}

carregarPlaylists();
