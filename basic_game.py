import tkinter as tk

class Chessboard(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Chessboard")
        self.geometry("400x400")

        self.canvas = tk.Canvas(self, width=400, height=400)
        self.canvas.pack()

        self.board = [
            ["R", "N", "B", "Q", "K", "B", "N", "R"],
            ["P", "P", "P", "P", "P", "P", "P", "P"],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            ["p", "p", "p", "p", "p", "p", "p", "p"],
            ["r", "n", "b", "q", "k", "b", "n", "r"]
        ]

        self.draw_chessboard()

        self.canvas.bind("<Button-1>", self.on_click)
        self.selected_piece = None

    def draw_chessboard(self):
        square_size = 50
        for row in range(8):
            for col in range(8):
                x0, y0 = col * square_size, row * square_size
                x1, y1 = x0 + square_size, y0 + square_size
                color = "white" if (row + col) % 2 == 0 else "black"
                self.canvas.create_rectangle(x0, y0, x1, y1, fill=color)

        for row in range(8):
            for col in range(8):
                piece = self.board[row][col]
                if piece != " ":
                    x, y = col * square_size + square_size // 2, row * square_size + square_size // 2
                    text_color = "green" if piece.isupper() else "blue"
                    self.canvas.create_text(x, y, text=piece, font=("Arial", 24), fill=text_color)

    def on_click(self, event):
        col, row = event.x // 50, event.y // 50

        if self.selected_piece is None:
            piece = self.board[row][col]
            if piece != " ":
                self.selected_piece = (row, col)
        else:
            selected_row, selected_col = self.selected_piece
            piece = self.board[selected_row][selected_col]
            self.board[selected_row][selected_col] = " "
            self.board[row][col] = piece
            self.draw_chessboard()
            self.selected_piece = None

if __name__ == "__main__":
    app = Chessboard()
    app.mainloop()
