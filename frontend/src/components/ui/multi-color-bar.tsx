export default function MultiColorBar() {
    return (
        <div className="w-full h-2 flex">
            <div className="w-1/5 bg-red-600"></div>
            <div className="w-1/5 bg-red-400"></div>
            <div className="w-1/5 bg-yellow-400"></div>
            <div className="w-1/5 bg-blue-400"></div>
            <div className="w-1/5 bg-purple-800"></div>
        </div>
    );
}