import React, { useEffect, useState, useRef } from 'react';
import ForceGraph2D from 'react-force-graph-2d';
import { fetchGraphData, autoGenerateGraph, createGraphConnection } from '../api';
import './GraphView.css'; // Importação do ficheiro CSS puro

export default function GraphView({userId}) {
  const [graphData, setGraphData] = useState({ nodes: [], links: [] });
  const graphRef = useRef();

  useEffect(() => {
    loadGraph();
  }, []);

  const loadGraph = async () => {
    try {
      const data = await fetchGraphData();
      setGraphData({nodes: data.vertices, links: data.edges});
    } catch (error) {
      console.error("Erro ao carregar o grafo:", error);
    }
  };

  const handleAutoGenerate = async () => {
    try {
      const data = await autoGenerateGraph(userId);
      setGraphData(data);
    } catch (error) {
      console.error("Erro ao gerar o grafo automaticamente:", error);
    }
  };

  const handleManualConnect = async () => {
    const source = prompt("ID do Documento 1:");
    const target = prompt("ID do Documento 2:");
    const keyword = prompt("Palavra-chave de ligação:");
    
    if (source && target) {
      try {
        await createGraphConnection(source, target, keyword);
        loadGraph(); // Recarrega o grafo após criar a ligação
      } catch (error) {
        alert("Erro ao criar a ligação manual.");
      }
    }
  };

  return (
    <div className="graph-container">
      {/* Botões sobrepostos ao grafo */}
      <div className="graph-controls">
        <button onClick={handleAutoGenerate} className="graph-btn btn-auto">
          Gerar Grafo Automático
        </button>
        <button onClick={handleManualConnect} className="graph-btn btn-manual">
          Adicionar Ligação Manual
        </button>
      </div>
      
      {/* Área onde a biblioteca desenha o grafo canvas */}
      <div className="graph-area">
        <ForceGraph2D
          ref={graphRef}
          graphData={graphData}
          nodeLabel="name"
          nodeAutoColorBy="group"
          linkColor={() => 'rgba(0,0,0,0.2)'}
          linkDirectionalArrowLength={3.5}
          linkDirectionalArrowRelPos={1}
          /* Opcional: ajustar para que o grafo ocupe o espaço corretamente em redimensionamentos */
          width={graphRef.current ? graphRef.current.offsetWidth : undefined}
          height={graphRef.current ? graphRef.current.offsetHeight : undefined}
        />
      </div>
    </div>
  );
}