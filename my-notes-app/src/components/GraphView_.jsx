import React, { useEffect, useState, useRef } from 'react';
import ForceGraph2D from 'react-force-graph-2d';
import { fetchGraphData, autoGenerateGraph, createGraphConnection } from '../api';

export default function GraphView() {
  const [graphData, setGraphData] = useState({ nodes: [], links: [] });
  const graphRef = useRef();

  useEffect(() => {
    loadGraph();
  }, []);

  const loadGraph = async () => {
    const data = await fetchGraphData(); // [cite: 82]
    setGraphData(data);
  };

  const handleAutoGenerate = async () => {
    const data = await autoGenerateGraph(); // [cite: 84]
    setGraphData(data);
  };

  const handleManualConnect = async () => { // [cite: 81]
    const source = prompt("ID do Documento 1:");
    const target = prompt("ID do Documento 2:");
    const keyword = prompt("Palavra-chave de conexão:"); // [cite: 79]
    if (source && target) {
      await createGraphConnection(source, target, keyword);
      loadGraph(); // Recarrega grafo
    }
  };

  return (
    <div className="flex flex-col h-full w-full relative">
      <div className="absolute top-4 left-4 z-10 flex gap-2">
        <button onClick={handleAutoGenerate} className="p-2 bg-blue-600 text-white rounded shadow">
          Gerar Grafo Automático
        </button>
        <button onClick={handleManualConnect} className="p-2 bg-green-600 text-white rounded shadow">
          Adicionar Conexão Manual
        </button>
      </div>
      <div className="flex-1 bg-gray-50">
        <ForceGraph2D
          ref={graphRef}
          graphData={graphData}
          nodeLabel="name"
          nodeAutoColorBy="group"
          linkColor={() => 'rgba(0,0,0,0.2)'}
          linkDirectionalArrowLength={3.5}
          linkDirectionalArrowRelPos={1}
        />
      </div>
    </div>
  );
}