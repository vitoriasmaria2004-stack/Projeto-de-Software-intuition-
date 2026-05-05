import React, { useState, useEffect } from 'react';
import Auth from './components/Auth';
import Editor from './components/Editor';
import GraphView from './components/GraphView';
import { createDocument, createFolder, getUserRoot } from './api';
import { Folder, FileText, Network, FilePlus, FolderPlus } from 'lucide-react'; // Adicionei os ícones de +
import './App.css'; // Importando o CSS puro

export default function App() {
  const [user, setUser] = useState(null);
  const [items, setItems] = useState([]);
  const [selectedDoc, setSelectedDoc] = useState(null);
  const [selectedFolder, setSelectedFolder] = useState(null);
  const [view, setView] = useState('editor'); // 'editor' ou 'graph'

  useEffect(() => {
    if (user) {
      getUserRoot(user.id).then((data) => {
        console.log(data);
        setItems(data.children);
        setSelectedDoc(data);
        setSelectedFolder(data);
      });
    }
  }, [user]);

  const handleCreateDoc = async () => {
    const title = prompt("Nome do novo documento:");
    if (title) {
      // Aqui você passaria o ID da pasta atual se estivesse dentro de uma. 
      // Por enquanto, criamos na raiz (simulado).
      const parentId = selectedDoc.type === 'folder' ? selectedDoc.id : selectedDoc.parentId;
      const newDoc = await createDocument(title, user.id, parentId);
      setSelectedDoc(newDoc);
      selectedFolder.children.push(newDoc);
      setView('editor');
      setItems(items);
    }
  };

  const handleCreateFolder = async () => {
    const name = prompt("Nome da nova pasta:");
    if (name) {
      const parentId = selectedDoc.type === 'folder' ? selectedDoc.id : selectedDoc.parentId;
      const newFolder = await createFolder(name, user.id, parentId);
      selectedFolder.children.push(newFolder);
      setSelectedDoc(newFolder);
      setItems(items);
    }
  };

  if (!user) return <Auth onLogin={setUser} />;

  // Renderizador recursivo de pastas e arquivos
  const renderTree = (nodes) => (
    <ul className="tree-list">
      {nodes.map(node => (
        <li key={node.id} className="tree-item">
          {node.type === 'folder' ? (
            <div>
              <span
              onClick={() => { setSelectedDoc(node); setSelectedFolder(node)}}
              className="folder-label">
                <Folder size={16}/> {node.name}
              </span>
              {node.children && renderTree(node.children)}
            </div>
          ) : (
            <span 
              onClick={() => { setSelectedDoc(node); setView('editor'); setSelectedFolder(node.parentId) }}
              className={`doc-label ${selectedDoc?.id === node.id ? 'active' : ''}`}
            >
              <FileText size={16}/> {node.name}
            </span>
          )}
        </li>
      ))}
    </ul>
  );

  return (
    <div className="app-container">
      {/* Sidebar */}
      <div className="sidebar">
        <div className="sidebar-header">
          <span>Intuition</span>
          {/* Adicionada uma div para agrupar os botões */}
          <div style={{ display: 'flex', gap: '4px' }}> 
            <button onClick={handleCreateFolder} className="icon-button" title="Nova Pasta">
              <FolderPlus size={18} />
            </button>
            <button onClick={handleCreateDoc} className="icon-button" title="Novo Documento">
              <FilePlus size={18} />
            </button>
            <button onClick={() => setView('graph')} className="icon-button" title="Visualizar Grafo">
              <Network size={18} />
            </button>
          </div>
        </div>
        <div className="tree-container">
          {renderTree(items)}
        </div>
      </div>

      {/* Área de Conteúdo Principal */}
      <div className="main-content">
        {view === 'graph' ? (
          <GraphView userId={user.id} />
        ) : (selectedDoc?.type == 'file') ? (
          <Editor document={selectedDoc} key={selectedDoc.id} />
        ) : (
          <div className="empty-state">
            Selecione um documento ou abra o Grafo de Conhecimento.
          </div>
        )}
      </div>
    </div>
  );
}