// src/api.js

const API_BASE_URL = 'http://localhost:8080';

// --- 1. Cadastro de usuário [cite: 14, 24] ---
export const loginUser = async (usernameOrEmail, password) => {
  try {
    const response = await fetch(`${API_BASE_URL}/user/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      // Nota: O seu back-end busca a chave "username" no Map, 
      // mesmo que a variável no Java se chame 'usernameOrEmail'.
      body: JSON.stringify({ 
        username: usernameOrEmail, 
        password: password 
      }),
    });

    if (!response.ok) {
      throw new Error(`Erro no login: credenciais inválidas ou erro no servidor`);
    }

    // Retorna o objeto User logado
    return await response.json(); 
  } catch (error) {
    console.error("Erro ao fazer login:", error);
    throw error;
  }
};

export const registerUser = async (username, email, password) => {
  try {
    const response = await fetch(`${API_BASE_URL}/user/signup`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ 
        username: username, 
        email: email, 
        password: password 
      }),
    });

    if (!response.ok) {
      throw new Error(`Erro no cadastro: ${response.status}`);
    }

    // Retorna o objeto User recém-criado
    return await response.json(); 
  } catch (error) {
    console.error("Erro ao registrar usuário:", error);
    throw error;
  }
};

// --- 2. Pastas e Documentos [cite: 19, 37, 61] ---
export const getUserRoot = async (userId) => {
  try {
    // Como é um @RequestParam, o userId vai na URL
    const response = await fetch(`${API_BASE_URL}/user/root?userId=${userId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    });

    if (!response.ok) {
      throw new Error(`Erro ao buscar raiz do usuário: ${response.status}`);
    }

    // Retorna o objeto Folder que representa a raiz
    return await response.json(); 
  } catch (error) {
    console.error("Erro ao buscar pasta raiz:", error);
    throw error;
  }
};

export const createDocument = async (name, userId, parentId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/file/document/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name: name,
        userId: userId.toString(),
        parentId: parentId.toString()
      }),
    });

    if (!response.ok) throw new Error(`Erro ao criar documento: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Erro ao criar documento:", error);
    throw error;
  }
};

export const createFolder = async (name, userId, parentId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/file/folder/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name: name,
        userId: userId.toString(),
        parentId: parentId.toString()
      }),
    });

    if (!response.ok) throw new Error(`Erro ao criar pasta: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Erro ao criar pasta:", error);
    throw error;
  }
};

export const moveFile = async (fileId, oldId, newId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/file/move`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        fileId: fileId.toString(),
        oldId: oldId.toString(),
        newId: newId.toString()
      }),
    });

    if (!response.ok) throw new Error(`Erro ao mover arquivo: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Erro ao mover arquivo:", error);
    throw error;
  }
};

export const renameFile = async (fileId, newName) => {
  try {
    const response = await fetch(`${API_BASE_URL}/file/rename`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        fileId: fileId.toString(),
        newName: newName
      }),
    });

    if (!response.ok) throw new Error(`Erro ao renomear arquivo: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Erro ao renomear arquivo:", error);
    throw error;
  }
};

export const updateDocument = async (docId, content) => {
  try {
    const response = await fetch(`${API_BASE_URL}/file/document/save`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        docId: docId.toString(),
        content: content
      }),
    });

    if (!response.ok) throw new Error(`Erro ao salvar conteúdo do documento: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Erro ao salvar documento:", error);
    throw error;
  }
};

// --- 3. Compartilhamento e Imagens [cite: 21, 22] ---
export const shareDocumentCopy = async (userId, docId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/file/send`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        userId: userId.toString(),
        docId: docId.toString()
      }),
    });

    if (!response.ok) throw new Error(`Erro ao enviar documento para o usuário: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Erro ao enviar documento:", error);
    throw error;
  }
};

export const uploadImage = async (file) => {
  console.log("Fazendo upload de imagem...", file);
  // Simula URL de retorno do S3/Cloudinary
  return "https://via.placeholder.com/300"; 
};

// --- 4. Grafo de Conhecimento [cite: 17, 73] ---
export const fetchGraphData = async (userId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/graph?userId=${userId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    });

    if (!response.ok) throw new Error(`Erro ao buscar grafo: ${response.status}`);
    
    // Retorna o GraphDTO
    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar dados do grafo:", error);
    throw error;
  }
};

export const createGraphConnection = async (userId, sourceId, destinationId, keyword) => {
  try {
    const response = await fetch(`${API_BASE_URL}/graph/edge`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      // Convertendo os IDs para string, pois o Java espera um Map<String, String>
      body: JSON.stringify({
        userId: userId.toString(),
        sourceId: sourceId.toString(),
        destinationId: destinationId.toString(),
        keyword: keyword
      }),
    });

    if (!response.ok) throw new Error(`Erro ao criar conexão no grafo: ${response.status}`);
    
    // Como o backend retorna void (apenas Status OK), retornamos apenas true
    return true; 
  } catch (error) {
    console.error("Erro ao criar conexão manual:", error);
    throw error;
  }
};


export const autoGenerateGraph = async (userId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/graph/generate?userId=${userId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    });

    if (!response.ok) throw new Error(`Erro ao gerar grafo automático: ${response.status}`);
    
    // O backend também retorna void aqui, então apenas indicamos sucesso
    return true;
  } catch (error) {
    console.error("Erro ao gerar grafo automaticamente:", error);
    throw error;
  }
};