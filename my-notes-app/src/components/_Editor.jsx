import React, { useState } from 'react';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import remarkMath from 'remark-math';
import rehypeKatex from 'rehype-katex';
import html2pdf from 'html2pdf.js';
import { updateDocument, shareDocumentCopy, uploadImage } from '../api';
import { Download, Share2, Image as ImageIcon, Save } from 'lucide-react';

export default function Editor({ document }) {
  const [content, setContent] = useState(document.content || '');

  const handleSave = async () => {
    await updateDocument(document.id, content);
    alert('Documento salvo!');
  };

  const handleExportPDF = () => { // 
    const element = document.getElementById('pdf-preview');
    html2pdf().from(element).save(`${document.title || 'documento'}.pdf`);
  };

  const handleShare = async () => { // [cite: 22]
    const email = prompt("Digite o email do usuário para enviar uma cópia:");
    if (email) {
      await shareDocumentCopy(document.id, email);
      alert('Cópia compartilhada com sucesso!');
    }
  };

  const handleImageUpload = async () => { // [cite: 21]
    // Na prática, abriria um input type="file"
    const url = await uploadImage("mock_file");
    setContent(content + `\n![Descrição](${url})\n`);
  };

  return (
    <div className="flex flex-col h-full w-full">
      <div className="flex p-2 bg-gray-200 gap-2">
        <button onClick={handleSave} className="p-2 bg-green-500 text-white rounded flex gap-1"><Save size={18}/> Salvar</button>
        <button onClick={handleExportPDF} className="p-2 bg-red-500 text-white rounded flex gap-1"><Download size={18}/> PDF</button>
        <button onClick={handleImageUpload} className="p-2 bg-blue-500 text-white rounded flex gap-1"><ImageIcon size={18}/> Imagem</button>
        <button onClick={handleShare} className="p-2 bg-purple-500 text-white rounded flex gap-1"><Share2 size={18}/> Compartilhar Cópia</button>
      </div>
      
      <div className="flex flex-1 overflow-hidden">
        {/* Textarea Markdown [cite: 15] */}
        <textarea 
          className="w-1/2 p-4 border-r resize-none focus:outline-none font-mono"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="Escreva em Markdown ou $$ LaTeX $$ aqui..."
        />
        
        {/* Preview Renderizado [cite: 16] */}
        <div id="pdf-preview" className="w-1/2 p-4 overflow-y-auto bg-white prose max-w-none">
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