package servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ValidateCodeServlet extends HttpServlet {
    private Random random = new Random();
    private static final int VALIDATE_CODE_NUM = 4;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String validate_code = createValidateCode(VALIDATE_CODE_NUM);
        int w = 100;
        int h = 40;
        BufferedImage image = paintValidateCode(w, h, validate_code);
        ImageIO.write(image, "PNG", response.getOutputStream());
        ;
    }

    /**
     * 创建n位数验证码
     *
     * @param n
     * @return
     */
    private String createValidateCode(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stringBuilder.append(random.nextInt(9));
        }
        return stringBuilder.toString();
    }

    /**
     * 画带线条的验证码
     *
     * @param w
     * @param h
     * @param validateCode
     */
    private BufferedImage paintValidateCode(int w, int h, String validateCode) {
        BufferedImage bf = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = bf.getGraphics();
        g.setColor(getRandomColor(50, 250));
        g.fillRect(0, 0, w, h);

        for (int i = 0; i < 100; i++) {
            g.setColor(getRandomColor(150, 200));
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            int x2 = random.nextInt(w);
            int y2 = random.nextInt(h);
            g.drawLine(x1, y1, x2, y2);
        }

        g.setFont(new Font("serif", Font.CENTER_BASELINE, 32));
        for (int i = 0; i < VALIDATE_CODE_NUM; i++) {
            String temp = validateCode.substring(i, i + 1);
            g.setColor(getRandomColor(200, 250));
            g.drawString(temp, 24 * i + 6, 30);
        }
        g.dispose();
        return bf;
    }

    /**
     * 得到随机背景色
     *
     * @param begin
     * @param end
     * @return
     */
    private Color getRandomColor(int begin, int end) {
        int r = begin + random.nextInt(end - begin);
        int g = begin + random.nextInt(end - begin);
        int b = begin + random.nextInt(end - begin);
        return new Color(r, g, b);
    }
}
