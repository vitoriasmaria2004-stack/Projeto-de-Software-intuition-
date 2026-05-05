import React, { useState } from 'react';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import remarkMath from 'remark-math';
import rehypeKatex from 'rehype-katex';
import html2pdf from 'html2pdf.js';
import { shareDocumentCopy, updateDocument } from '../api'; // Importações atualizadas da API
import { Download, Share2, Image as ImageIcon, Save } from 'lucide-react';
import './Editor.css'; // Importando o CSS

export default function Editor({ document }) {
  // Inicializa com o conteúdo que vier do banco, ou vazio se for novo
  const [content, setContent] = useState(document.content || '');

  const handleSave = async () => {
    try {
      await updateDocument(document.id, content);
      alert('Documento salvo com sucesso!');
    } catch (error) {
      alert('Erro ao salvar documento.');
    }
  };

  const handleExportPDF = () => {
    const element = window.document.getElementById('pdf-preview');
    const opt = {
      margin:       10,
      filename:     `${document.name || 'documento'}.pdf`, // O FileController usa 'name'
      image:        { type: 'jpeg', quality: 0.98 },
      html2canvas:  { scale: 2 },
      jsPDF:        { unit: 'mm', format: 'a4', orientation: 'portrait' }
    };
    html2pdf().set(opt).from(element).save();
  };

  const handleShare = async () => {
    // Como o backend (FileController) espera um userId, pedimos o ID
    const targetUserId = prompt("Digite o ID do usuário para enviar uma cópia:");
    if (targetUserId) {
      try {
        await shareDocumentCopy(targetUserId, document.id);
        alert('Cópia enviada com sucesso!');
      } catch (error) {
        alert('Erro ao enviar cópia.');
      }
    }
  };

  const handleImageUpload = () => {
    // Isso é um mockup. Para upload real, você precisaria de uma rota no backend 
    // ou integrar com AWS S3 / Cloudinary e pegar a URL de retorno.
    const url = prompt("Insira o link (URL) da imagem:");
    if (url) {
      setContent(content + `\n![Descrição da imagem](${url})\n`);
    }
  };

  return (
    <div className="editor-container">
      {/* Toolbar */}
      <div className="editor-toolbar">
        <button onClick={handleSave} className="toolbar-btn btn-save">
          <Save size={18}/> Salvar
        </button>
        <button onClick={handleExportPDF} className="toolbar-btn btn-pdf">
          <Download size={18}/> Exportar PDF
        </button>
        <button onClick={handleImageUpload} className="toolbar-btn btn-image">
          <ImageIcon size={18}/> Inserir Imagem
        </button>
        <button onClick={handleShare} className="toolbar-btn btn-share">
          <Share2 size={18}/> Enviar Cópia
        </button>
      </div>
      
      {/* Workspace */}
      <div className="editor-workspace">
        {/* Lado esquerdo: Digitação */}
        <textarea 
          className="editor-textarea"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="Escreva em Markdown ou $$ LaTeX $$ aqui..."
        />
        
        {/* Lado direito: Visualização */}
        <div id="pdf-preview" className="editor-preview">
          <ReactMarkdown 
            remarkPlugins={[remarkGfm, remarkMath]} 
            rehypePlugins={[rehypeKatex]}
          >
            {content}
          </ReactMarkdown>
        </div>
      </div>
    </div>
  );
}