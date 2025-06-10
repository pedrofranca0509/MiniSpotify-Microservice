const token = localStorage.getItem("token");
if (!token) {
  window.location.href = "/login/login.html";
}

// Função para decodificar JWT e extrair o ID do usuário
function parseJwt(token) {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch (e) {
    return null;
  }
}

const decoded = parseJwt(token);
const usuarioId = decoded?.id || decoded?.sub;

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

document.addEventListener("DOMContentLoaded", () => {
  const btnPlay = document.getElementById("btn-play");
  const btnPlayPause = document.getElementById("btn-playpause");
  const btnNext = document.getElementById("btn-next");
  const btnPrev = document.getElementById("prev");

  if (btnPlay) btnPlay.addEventListener("click", tocarMusicaAtual);
  if (btnNext) btnNext.addEventListener("click", proximaMusica);
  if (btnPrev) btnPrev.addEventListener("click", musicaAnterior);

  if (btnPlayPause) {
    btnPlayPause.addEventListener("click", () => {
      const player = document.getElementById("player");
      if (player.paused) {
        player.play();
        btnPlayPause.innerText = "⏸️";
      } else {
        player.pause();
        btnPlayPause.innerText = "▶️";
      }
    });
  }
  document.getElementById("btn-removerPlaylist").addEventListener("click", removerPlaylist);
});

document.getElementById("btn-criarPlaylist").addEventListener("click", criarPlaylist);
document.getElementById("btn-adicionarMusica").addEventListener("click", adicionarMusica);

function criarPlaylist() {
  const nome = document.getElementById("novaPlaylistNome").value;

  // ✅ Correção: enviando o usuário autenticado via ID extraído do token JWT
  fetch("/api/playlists", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify({
      nome: nome,
      usuarioId: usuarioId, 
      musicasIds: [] 
    })
  })
    .then((res) => {
      if (!res.ok) throw new Error("Erro ao criar playlist");
      return res.json();
    })
    .then(() => {
      alert("Playlist criada com sucesso");
      carregarPlaylists();
    })
    .catch((err) => {
      console.error("Erro:", err);
      alert("Erro ao criar playlist");
    });
}

async function adicionarMusica() {
  const titulo = document.getElementById("musicaTitulo").value;
  const playlistIndex = document.getElementById("playlistSelect").value;
  const playlist = playlists[playlistIndex];

  if (!playlist) {
    alert("Selecione uma playlist válida.");
    return;
  }

  try {
    const res = await fetch(`/api/musicas/buscar?titulo=${encodeURIComponent(titulo)}`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!res.ok) throw new Error("Música não encontrada");

    const musica = await res.json();
    const musicaId = musica.id;

    const updateRes = await fetch(`/api/playlists/${playlist.id}/musicas`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({ musicasIds: [musicaId] })
    });

    if (!updateRes.ok) throw new Error("Erro ao adicionar música à playlist");

    alert("Música adicionada com sucesso!");
    carregarPlaylists();
  } catch (err) {
    console.error("Erro:", err);
    alert("Erro ao adicionar música");
  }
}

carregarPlaylists();
